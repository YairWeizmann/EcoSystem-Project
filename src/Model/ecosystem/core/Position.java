package Model.ecosystem.core;

@SuppressWarnings("all")
public class Position
{
    // ============ Fields ============
    private int m_row;
    private int m_col;

    // ============ Constructors ============
    public Position(int row , int col)
    {
      this.m_row = row;
      this.m_col = col;
    }

    // ============ Class Methods ============
    public int distanceTo(Position other)
    {
        return Math.abs(this.m_row - other.getRow()) + Math.abs(this.m_col - other.getCol());
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
            return true;

        if(!(o instanceof Position))
        {
            return false;
        }

        Position otherPos = (Position) o;
        boolean isEqual = this.m_row == otherPos.getRow() && this.m_col == otherPos.getCol();

        return isEqual;

    }


    // ============ Getters & Setters ============
    public int getRow()
    {
        return this.m_row;
    }

    public boolean setRow(int row)
    {
        if(row < 0)
            return false;
        this.m_row = row;
        return true;
    }

    public int getCol()
    {
        return this.m_col;
    }

    public boolean setCol(int col)
    {
        if(col < 0)
            return false;

        this.m_col = col;
        return true;
    }
}
