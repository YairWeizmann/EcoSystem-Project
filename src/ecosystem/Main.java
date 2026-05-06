import ecosystem.core.Environment;
import ecosystem.core.Position;
import ecosystem.entities.animals.Lion;
import ecosystem.entities.animals.Rabbit;
import ecosystem.entities.animals.Deer;
import ecosystem.entities.plants.OakTree;
import ecosystem.entities.plants.Flower;
import ecosystem.entities.resources.Rock;
import ecosystem.entities.resources.Water;

void main()
{
  Environment env = new Environment();

  env.addEntity(new Lion(new Position(2, 3)));
  env.addEntity(new Rabbit(new Position(8, 5)));
  env.addEntity(new Deer(new Position(1, 7)));



  env.printMap();
}
