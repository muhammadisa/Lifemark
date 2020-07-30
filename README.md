

<h1 align="center">Welcome to Lifemark 👋</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
  <a href="#" target="_blank">
    <img alt="License: MIT" src="https://img.shields.io/badge/License-MIT-yellow.svg" />
  </a>
  <img alt="documentation: yes" src="https://img.shields.io/badge/Documentation-Yes-green.svg" />
  <img alt="maintained: yes" src="https://img.shields.io/badge/Maintained-Yes-green.svg" />
</p>


> Short introduction, this is very simple internet checker, you can execute a function when the device disconnected and otherwise, you can check is connected to the internet for once on execution.

## Demo

<img src="lifemark-demo.gif" width="250">

## Install

For installation just add this code in your app build.gradle file

```groovy
implementation 'com.github.muhammadisa:lifemark:1.0.0'
```

## Simple Usage

1. Forever Checking

   ```kotlin
   val networkConnection = Lifemark(applicationContext)
   networkConnection.ObservableNetworkCondition()
   	.observe(this@MainActivity, Observer { isConnected ->
   	if (isConnected) {
           // do something when device connected to internet
   	} else {
           // do something when device disconnected to internet
   	}
   })
   ```

2. Checking for once execution

   ```kotlin
   val networkConnection = Lifemark(applicationContext)
   if(networkConnection.isNetworkConnected()){
       // do something when device connected to internet
   }else{
       // do something when device disconnected to internet
   }
   ```

##### Full Example

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val networkConnection = Lifemark(applicationContext)

        // Forever Checking example
        networkConnection.ObservableNetworkCondition()
            .observe(this@MainActivity, Observer { isConnected ->
                if (isConnected) {
                    disconnected_layout.visibility = View.GONE
                    connected_layout.visibility = View.VISIBLE
                } else {
                    connected_layout.visibility = View.GONE
                    disconnected_layout.visibility = View.VISIBLE
                }
            })

        if (networkConnection.isNetworkConnected()) {
            // do something when device connected to internet
            Log.d("INTERNET_CHECKER", "true")
        } else {
            // do something when device disconnected to internet
            Log.d("INTERNET_CHECKER", "false")
        }
    }

}
```

## Author

👤 **Dimas Prasetya**

* Github: [@Dimas-Prasetya](https://github.com/Dimas-Prasetya)

👤 **Muhammad Isa Wijaya Kusuma**

* Github: [@muhammadisa](https://github.com/muhammadisa)

## Show your support

Give a ⭐️ if this project helped you!

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_

