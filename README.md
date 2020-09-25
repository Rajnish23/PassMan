# PassMan
This project has several concepts of Kotlin implemented into a simple demo aaplication performing various tasks.

The Android Application is a simple Password Manager (hence the name ***PassMan***) that has three activities. One for logging in, another to change password and last one to list all the accounts with its details.

As of now,the values of acoounts and its details are hardcoded as its just a _demo application_ and will be integrated with database(probably **Google Firebase** as its easy and reliable) further.

The idea behind making this application is that we can just remember one password and can add,update all other passwords for different accounts at one place(and this will be achieved after the applicated is fully integrated with a backend). 

##### Features to be included :
- Adding new account details which will be done using a dialog box popping up on Floating Action Button click
- Updating existing details of an account
- Deleting an existing account
- Copying data on long click on the account info

The project still suffices to meet all the guidelines mentioned for the participation certificate.

### Concepts and terminologies sucessfully used :

- Subclasses/Inheritance
- Objects and Companion Objects
- Extensions
- Lambdas and Higher Order Functions
- Reflection
- Default Parameter Passing
- Extending Abstract Class
- Data Class


### How are the above concepts used ? :

- **Subclasses/Inheritance** : Every activity in the Application (PassMan) extends 'AppCompactActivity'. All the functions of the superclass has been over-ridden and used in activities of the application.
```kotlin
class ActivityHome : AppCompatActivity() {

    val accountDetails = ArrayList<AccountDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initData()
        setRecyclerView()
    }
```
> In the above code the _ActivityHome_ activity is a subclass of _AppCompactActivity_



- **Objects and Companion Objects** : Object _Constants_ has been used to contain constants in a seperate Kotlin file and use it anywhere by just accessing the constant using **<object_name>.<variable_name>**
```kotlin
object Constants {
    val clickButton = "Button Is Clicked"
}
```
> This is the object created for constants

```kotlin
private fun onLogin() {

        Log.i(TAG,Constants.clickButton)
        val pb_loading: ProgressBar = findViewById(R.id.pbLoading)
.
.
.
```
> This is how it is been accessed



- **Extensions** : An extension function called _showToast()_ is created which is of the superclass _Context_ without editing the actual class. It can directly be accessed as Context class is automatically inherited by all the activities.
```kotlin
fun Context.showToast(message: String,duration: Int = Toast.LENGTH_LONG){
    Toast.makeText(this,message,duration).show()
}
```
> This is the extension function
```kotlin
showToast(getText(R.string.success).toString())
```
> This is how it is been accessed



- **Lambdas & Higher Order Functions** : Many lambdas and higher order functions are used in seperate files for it to be reusable everywhere. One of it is _isNullLambda_ as lambda function and _canProceed()_ as the higher order function. 
```kotlin
class ReusableTasks(context: Context) {

    //Lambda function to check if string is null
    var isNullLambda: (String) -> Boolean = {str -> str.isEmpty()}

    //Higher order function for further action after string check
    fun canProceed(string: String, lamFunct: (String) -> Boolean = isNullLambda): Boolean{
        return !lamFunct(string)
        }
}
```
> The higher order function uses the lambda as one of its parameters

```kotlin
.
.
val performTask = ReusableTasks(this)
when (performTask.canProceed(enteredPass)){
            true -> {
                when (session.proceedAfterCheck(enteredPass)){
                    true -> { return true }
.
.
```
> This is how it is been accessed




- **Reflection** : The application uses reflection while passing explicit intents fron onr activity to another. 
```kotlin
val intent = Intent(this,ActivityHome::class.java)
            startActivity(intent)
```
> This is how it is been used



- **Default Parameter Passing** : The application has functions which use the feature of default values for parameters. 
```kotlin
fun canProceed(string: String, lamFunct: (String) -> Boolean = isNullLambda): Boolean{
        return !lamFunct(string)
        }
```
> The above code uses _isNullLambda_ as a default value to the _lamFunct_ parameter until specifically provided

```kotlin
fun Context.showToast(message: String,duration: Int = Toast.LENGTH_LONG){
    Toast.makeText(this,message,duration).show()
}
```
> The above code uses _Toast.LENGTH_LONG_ as the defauls value to the _duration_ parameter until specifically provided



- **Extending Abstract Class** : In the _AccountDetailsAdapter_ file for the recyclerview, the _AdapterDetailsVH_ class extends the _RecyclerView.ViewHolder_ abstract class. Hence the three functions viz _getItemCount()_, _onBindViewHolder()_ & _onCreateViewHolder()_ are to be implemented to the class compulsorily.
```kotlin
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountDetailsVH {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_elements,parent,false)
        return AccountDetailsVH(view)
    }

override fun getItemCount(): Int {

        return  detailList.size
    }

override fun onBindViewHolder(holder: AccountDetailsVH, position: Int) {
        val accountDetails: AccountDetails = detailList[position]
        holder.tv_account.text = accountDetails.accountName
.
.
.
}
```



- **Data Class** : The class _AccountDetails_ which is the model class to the adapter of recyclerview is a data class containing all the variables declared in each of the accounts to be displayed in the application.
```kotlin
class AccountDetails(val accountName: String, val email: String, val password: String,var expandable: Boolean = false){
}
```
