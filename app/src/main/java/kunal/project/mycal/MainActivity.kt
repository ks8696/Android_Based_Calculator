package kunal.project.mycal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var text: TextView? = null
    private var lastnumeric: Boolean = false
    private var lastdot: Boolean = false
    private var tominus:Boolean=false
    private var equal:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text = findViewById(R.id.textView)
    }

    fun onDigit(view: View) {
        if (equal) {
            text?.text = (view as Button).text
            lastdot = false
            tominus = false
            lastnumeric = true
            equal=false
        }
        else{
            text?.append((view as Button).text)
            lastnumeric = true
        }
        }

    fun clear(view:View) {
        text?.text?.let {
            if (equal) {
                text?.text = ""
                lastdot = false
                tominus = false
                lastnumeric = false
                equal=false
            } else {try{
               text?.text= it.substring(0,it.length-1)
                lastdot = false
                tominus = false
                lastnumeric = true
                equal=false}catch (e:Exception){
                Toast.makeText(this, "Field already CLEAR!!", Toast.LENGTH_SHORT).show()}
            }
        }
    }

    fun decimalDot(view: View) {
        text?.text?.let {
            if (it.toString().isEmpty() || (it.startsWith("-")&& !lastdot)) {
                text?.append((view as Button).text)
                lastdot=true
            } else if (lastnumeric && !lastdot && !equal) {
                text?.append((view as Button).text)
                lastnumeric = false
                lastdot = true
            }
        }
    }
    fun equals(view: View) {
        var txt = text?.text.toString()
        var prefix = ""
        try {
            if (lastnumeric) {
                if (txt.startsWith("-")) {
                    prefix = "-"
                    txt = txt.substring(1)
                }
                if (txt.contains("-")) {
                    val k = txt.split("-")
                    var x = k[0]
                    val y = k[1]
                    if (prefix.isNotEmpty()) {
                        x = prefix + x
                    }
                    val result = x.toDouble() - y.toDouble()
                    text?.text = dot(result.toString())
                    lastnumeric=true
                    tominus=false
                    equal=true
                }
               else if (txt.contains("+")) {
                    val k = txt.split("+")
                    var x = k[0]
                    val y = k[1]
                    if (prefix.isNotEmpty()) {
                        x = prefix + x
                    }
                    val result = x.toDouble() + y.toDouble()
                    text?.text = dot(result.toString())
                    lastnumeric=true
                    tominus=false
                    equal=true
                }
                else if (txt.contains("*")) {
                    val k = txt.split("*")
                    var x = k[0]
                    val y = k[1]
                    if (prefix.isNotEmpty()) {
                        x = prefix + x
                    }
                    val result = x.toDouble() * y.toDouble()
                    text?.text = dot(result.toString())
                    lastnumeric=true
                    tominus=false
                    equal=true
                }
                else if (txt.contains("/")) {
                    val k = txt.split("/")
                    var x = k[0]
                    val y = k[1]
                    if (prefix.isNotEmpty()) {
                        x = prefix + x
                    }
                    val result = x.toDouble() / y.toDouble()
                    text?.text = dot(result.toString())
                    lastnumeric=true
                    tominus=false
                    equal=true
                }
            }
        } catch (e:Exception) {
            Toast.makeText(this, "Dude I Just Can't", Toast.LENGTH_SHORT).show()
        }
    }

    fun dot(str:String):String{
        var res=str
        if(res.contains(".0")){
            res=res.substring(0,res.length-2)
        }
        return res
    }

    fun no_operator(view: View) {
        text?.text?.let {
        if (it.toString().isEmpty() && (view as Button).text=="-") {
            text?.text = "-"
        } else if (lastnumeric && !noother(it.toString()) && !tominus) {
            text?.append((view as Button).text)
            lastnumeric = false
            lastdot = false
            tominus=true
            equal=false
        }
    }
    }

    private fun noother(str: String): Boolean {
        return if (str.startsWith("-")) {
            false
        } else {
            str.contains("+") || str.contains("-") || str.contains("/") || str.contains("*")
        }
    }
}