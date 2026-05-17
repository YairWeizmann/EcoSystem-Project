import ecosystem.core.Environment;
import ecosystem.core.Position;
import ecosystem.core.SimulationEngine;
import ecosystem.entities.animals.Lion;
import ecosystem.entities.animals.Rabbit;
import ecosystem.entities.animals.Deer;
import ecosystem.entities.plants.OakTree;
import ecosystem.entities.plants.Flower;
import ecosystem.entities.plants.Plant;
import ecosystem.entities.resources.Rock;
import ecosystem.entities.resources.Water;

void main()
{
  Environment env = new Environment();

  env.addEntity(new Lion(new Position(2, 3)));//Lion
  env.addEntity(new Rabbit(new Position(7, 4)));//Rabbit
  env.addEntity(new Deer(new Position(1, 7))); //Deer
  env.addEntity(new Water(new Position(1,2))); //Water
  env.addEntity(new OakTree(new Position(3 ,3)));
  env.addEntity(new Flower(new Position(1 ,3)));


  SimulationEngine simulationEngine = new SimulationEngine(env);


  simulationEngine.tick();
  simulationEngine.tick();
  simulationEngine.tick();
  simulationEngine.tick();
}
