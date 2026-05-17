package ecosystem.entities;
import ecosystem.core.Position;
@SuppressWarnings("all")

public abstract class AbstractEntity
{

    // ===================== FIELDS =====================
   private Position m_position; //Entity Position
   private char m_symbol; //Symbol on the Map
   private boolean is_alive = true; // is Entity Alive

    // ===================== Constructors =====================
    public AbstractEntity(Position position, char symbol, boolean isAlive)
    {
       this.m_position = position;
       this.m_symbol = symbol;
       this.is_alive = isAlive;
    }
    public AbstractEntity()
    {
        this.m_position = null;
        m_symbol = 'D'; // D Stands for Deafult
        this.is_alive = false;
    }


    // ===================== Methods =====================
    @Override
    public String toString() // Returns a detailed String About the Object
    {
        return getClass().getSimpleName() +
                " " + m_position +
                " Alive=" + is_alive;
    }

    @Override
    public boolean equals(Object o) //Check if Object is Equal
    {
       if(this == o)
           return true;

       if(!(o instanceof AbstractEntity))
           return false;

       AbstractEntity other = (AbstractEntity) o;

       boolean objectEquals = this.m_position.equals(other.getM_position()) // Position , Symbol . isAlive all Equal
               && this.m_symbol == other.getM_symbol()
               && this.is_alive == other.getIs_alive();

       return objectEquals;
    }

    // ===================== Getters & Setters =====================

    public Position getM_position()
    {
        return m_position;
    }

    public boolean setM_position(Position position)
    {
        if(position == null)
            return false;

        this.m_position = position;
        return true;
    }

    public char getM_symbol()
    {
        return m_symbol;
    }

    public boolean setM_symbol(char symbol)
    {
        if (symbol == '\u0000') // invalid / not set
            return false;

        this.m_symbol = m_symbol;
        return true;
    }

    public boolean getIs_alive()
    {
        return is_alive;
    }

    public boolean setIs_alive(boolean is_alive)
    {
        this.is_alive = is_alive;
        return true;
    }
}
