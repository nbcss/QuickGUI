# QuickGUI
Updated to Spigot1.11.2

The API used packet modification to send dummy inventory page, and packging various type of gui and button into classes which are easy to use. 

All buttons can set their own click event handler. 

23/4 update

All new empty slots in AbstractInventory will use AIR icon now. 

Add new events and attributes for some Inventory classes, such as Beacon enchanment and Fuel of Furance. 

Added new Synachronizable interface; all subclass of AbstractInventory which implemented this interface will update all opener of the GUI for different players, so the contents can be synachronized across different players when update. All defult implementations of Inventory did not implement this interface, user need to create their own subclass if they want to use the feature. 

21/4 update

Added new VillagerTradeInventory, which allow to custom the trade page of villager. 

Add new attributes for some Inventory classes, such as AnvilInventory now have cost attribute which can modify. 

19/4 update

InventoryView objects can now change its topInventory,bottomInventory and hotbarInventory for change its page. 

InventoryView now have its own Title; the defualt title will use the title of its topInventory. 

InventoryView added 'watchers' to allow multiple users to access same GUI. 
