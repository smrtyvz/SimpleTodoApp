# SimpleTodoApp
This is a simple todo list application developed with Kotlin and MVVM+Room+LiveData

## Tech/framework used

<b>Built with</b>
- [Room](https://developer.android.com/training/data-storage/room/)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [MVVM pattern](https://medium.com/upday-devs/android-architecture-patterns-part-3-model-view-viewmodel-e7eeee76b73b)


An extra abstraction layer is used to provide a simple solution to use different methods if any web service data requires. 
To do this, **TodoItemRepository** class is created.Repository class occurs between **Data Access Object and View Model** layers. 
It calls the DAO methods by getting order from ViewModel.

## Screenshots

![todo list](https://raw.githubusercontent.com/smrtyvz/SimpleTodoApp/master/images/TodoList.png)
![add todo](https://raw.githubusercontent.com/smrtyvz/SimpleTodoApp/master/images/Add_Todo.png)
![edit todo](https://raw.githubusercontent.com/smrtyvz/SimpleTodoApp/master/images/Edit_Todo.png)
