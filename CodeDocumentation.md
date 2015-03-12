# Introduction #

This page describes each of the classes implemented in the project.


# Details #

Boom: This is a simple class with x, y, and radius attributes used to track the location of explosions.

ExpertSystem: This is a wrapper for a Drools stateless expert system that loads a .drl file and is passed a Rocket object to apply rules to.

Rocket: Base class for Missile and Ship.  Contains x, y, dx, dy, and angle attributes.  It also allows for variable acceleration (missiles are about 10 times faster than ships in our simulation).   Finally, it has an enemyShip and enemyMissile attribute that it uses to make other information (enemy distance, enemy angle, enemy missile distance, enemy missile angle) available for access by the rules.

Missile: Inherits Rocket.  A ship is thought to have an ion or fusion drive; that is, fuel on a ship is limitless.  However, a missile isn't large enough to contain such a device, so it has a limited amount of fuel.  The missiles are programmed to detonate once a fuel threshold is met, as the actual explosive for the missile is also it's fuel.

Ship: Inherits Rocket.  Keeps track of amount of missiles (right now, we limit the ships to one missile to keep the simulations short).  Also keeps track of if a missile fire request was made by the expert system.

Util: Contains utility methods for use elsewhere in the program.

SpaceSim: Entry point of the program.  Contains all initialization code and gui code.

joe.drl, darnell.drl: Rules for the ships.  Joe is the north ship and Darnell is the south ship.  The designations came from when the ships started at fixed positions; now, the north ship is green and the south ship is red, but they can start in any position.

joe\_missile.drl, darnell\_missile.drl: Rules for the missiles.