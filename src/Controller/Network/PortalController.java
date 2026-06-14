package Controller.Network;

import Model.ecosystem.core.Environment;
import Model.ecosystem.entities.AbstractEntity;
import Model.ecosystem.entities.LivingEntity;
import Model.ecosystem.entities.animals.Animal;
import Model.ecosystem.network.NetworkManager;
import View.Map.MapPanel;

public class PortalController
{
    private Environment m_environment;
    private NetworkManager m_networkManager;
    private MapPanel m_mapPanel;

    public PortalController(Environment environment, NetworkManager networkManager, MapPanel mapPanel)
    {
        this.m_environment = environment;
        this.m_networkManager = networkManager;
        this.m_mapPanel = mapPanel;
    }

    public boolean sendToPortal(AbstractEntity selectedEntity, String targetIp)
    {
        if (selectedEntity == null)
        {
            System.out.println("Cannot send to portal: no entity selected.");
            return false;
        }

        if (!(selectedEntity instanceof Animal))
        {
            System.out.println("Cannot send to portal: only animals can migrate.");
            return false;
        }

        if (targetIp == null || targetIp.isBlank())
        {
            System.out.println("Cannot send to portal: target IP is empty.");
            return false;
        }

        String message = createSpawnMessage(selectedEntity);
        boolean sent = m_networkManager.sendMessage(targetIp.trim(), message);

        if (!sent)
        {
            System.out.println("Portal send failed. Entity was not removed.");
            return false;
        }

        // Only remove the animal after the message was sent successfully.
        stopEntityThread(selectedEntity);
        m_environment.removeEntity(selectedEntity);
        m_mapPanel.repaint();

        System.out.println("Entity sent through portal and removed locally: " + selectedEntity.getM_entityType());
        return true;
    }

    private String createSpawnMessage(AbstractEntity entity)
    {
        int energy = 0;

        if (entity instanceof LivingEntity)
        {
            energy = (int) ((LivingEntity) entity).getM_energy();
        }

        int x = entity.getM_position().getCol();
        int y = entity.getM_position().getRow();

        return "SPAWN," + entity.getM_entityType() + "," + energy + "," + x + "," + y;
    }

    private void stopEntityThread(AbstractEntity entity)
    {
        if (entity instanceof Animal)
        {
            ((Animal) entity).stopThread();
        }
    }
}
