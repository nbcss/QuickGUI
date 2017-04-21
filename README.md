# QuickGUI
已更新到Spigot1.11.2

通过拦截/发送库存包来模拟虚拟的库存页面的API，将各种库存类型和按钮封装成了多个易于使用的类。

各个按钮均可以设定自己的点击事件，库存事件尚未添加。

21/4 update

添加了新的VillagerTradeInventory 现在允许自定义交易面板的物品交易内容显示等。

为部分Inventory添加了一些额外数据，例如AnvilInventory现在有cost显示可更改(维修需求等级/Repair Level)

19/4 update

InventoryView对象现在允许重设其topInventory,bottomInventory与hotbarInventory来实现界面切换。

InventoryView现拥有自己的Title，默认title将使用topInventory的title。

InventoryView添加了watchers观测者列表来实现多人使用同界面。
