package com.example.calculator

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityMainBinding: ActivityMainBinding
    private var infixString :String = "";
    private var parenthesisOpen : Boolean = false
    private var color_init:Int = 0;

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.copy_paste,menu)
        val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var pasteData:String = ""

        val pasteItem = menu?.findItem(R.id.paste)
        // Setting Paste Item Enabled if it contains valid data
        if (pasteItem != null) {
            pasteItem.isEnabled = when{
                ! clipBoard.hasPrimaryClip() -> {false}
                ! (clipBoard.primaryClipDescription?.hasMimeType(MIMETYPE_TEXT_PLAIN))!! -> {false}
                else -> {true}
            }
        }
    }



    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mainActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainActivityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        color_init = R.color.white;
        handleClickListener(mainActivityMainBinding.parOpen,"(")
        handleClickListener(mainActivityMainBinding.parClose,")")
        handleClickListener(mainActivityMainBinding.one,"1")
        handleClickListener(mainActivityMainBinding.two,"2")
        handleClickListener(mainActivityMainBinding.three,"3")
        handleClickListener(mainActivityMainBinding.four,"4")
        handleClickListener(mainActivityMainBinding.five,"5")
        handleClickListener(mainActivityMainBinding.six,"6")
        handleClickListener(mainActivityMainBinding.seven,"7")
        handleClickListener(mainActivityMainBinding.eight,"8")
        handleClickListener(mainActivityMainBinding.nine,"9")
        handleClickListener(mainActivityMainBinding.zero,"0")
        handleClickListener(mainActivityMainBinding.dot,".")
        handleClickListener(mainActivityMainBinding.div,"/")
        handleClickListener(mainActivityMainBinding.mul,"*")
        handleClickListener(mainActivityMainBinding.sub,"-")
        handleClickListener(mainActivityMainBinding.add,"+")
        handleClickListener(mainActivityMainBinding.dot,".")


        mainActivityMainBinding.ac.setOnClickListener{
            infixString = ""

            mainActivityMainBinding.textArea.text = "0"
            mainActivityMainBinding.inputString.text = ""
//            mainActivityMainBinding.textArea.setTextColor(R.color.white)
        }

        mainActivityMainBinding.bs.setOnClickListener {

            if (infixString.length == 1){
                infixString = ""
//                mainActivityMainBinding.textArea.setTextColor(R.color.gray)
                mainActivityMainBinding.textArea.text = "0"
            }
            else if(infixString != ""){
                infixString = infixString.substring(0,infixString.length-1)
//                mainActivityMainBinding.textArea.setTextColor(R.color.gray)
                mainActivityMainBinding.textArea.text = infixString
            }
        }
        mainActivityMainBinding.eq.setOnClickListener {
            var postfix:String = infixToPostfix(infixString)
            if (postfix != "Syntax Error"){
                var result = evaluatePostfix(postfix);
                mainActivityMainBinding.inputString.text = infixString
                mainActivityMainBinding.textArea.text = result
                infixString = result
            }
            else{
                mainActivityMainBinding.textArea.text = "Syntax Error"
            }

        }


        mainActivityMainBinding.inputString.setOnLongClickListener {
            val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("input_text",mainActivityMainBinding.inputString.text)
            clipBoard.setPrimaryClip(clipData)
            true
        }

        registerForContextMenu(mainActivityMainBinding.textArea)

    }
    private fun handleClickListener(button: AppCompatButton,string: String){
        button.setOnClickListener {
            if (string in "+-*/") {
                // Handle operators
                if (infixString.isEmpty()) {
                    // If infixString is empty, do not add operator
                    return@setOnClickListener
                }
                val lastChar = infixString.last()
                if (lastChar in "+-*/") {
                    // If the last character in infixString is an operator, replace it with the new operator
                    infixString = infixString.dropLast(1) + string
                } else {
                    // Otherwise, simply append the operator
                    infixString += string
                }
            } else {
                // Handle numerals and other characters
                infixString += string
            }
            mainActivityMainBinding.textArea.text = infixString
        }
//        button.setOnClickListener {
//            infixString += string
//            mainActivityMainBinding.textArea.text = infixString
//        }
    }
    private fun precedence(c:String):Int{
        if(c == "*"|| c == "/")
            return 3;
        else if(c == "-"||c == "+")
            return 2;
        else
            return 1;
    }
    private fun evaluatePostfix(string:String):String{

        var stack = ArrayDeque<Float>()
        var int_value:String = "";
        var bool_value:Boolean = false;

        for(i in 0..string.length-1){
            if(string[i] == '['){
                bool_value = true;
                int_value = "";
                continue;
            }
            if(string[i] == ']'){
                bool_value = false;
                stack.addLast(int_value.toFloat())

            }
            if(bool_value == true){
                int_value += string[i];
            }


            else if(bool_value == false && string[i]!='[' && string[i]!=']'){
                if(stack.size < 2){
                    return "Syntax Error"
                }
                var operand2 = stack[stack.size-1];stack.removeLast();
                var operand1 = stack[stack.size-1];stack.removeLast();

                when(string[i]){
                    '+' ->{stack.addLast(operand1+operand2)}
                    '-' ->{stack.addLast(operand1-operand2)}
                    '*' ->{stack.addLast(operand1*operand2)}
                    '/' ->{
                        if (operand2 == 0.toFloat()){
                            return "Math Error"
                        }
                        stack.addLast(operand1/operand2);
                    }
                }
            }

        }
        if (stack.size == 1) {
            val result = stack[0].toString()
            if(result.endsWith(".0")){
                return result.replace(".0","")
            }
            else{
                return result
            }
        }
        else
            return "Syntax Error"
    }
    private fun infixToPostfix(string:String):String {

        var stack = ArrayDeque<String>();
        var v:String = "[";
        var result:String = "";
        var i:Int;

        for(i in 0..string.length-1){
            if ((string[i].toInt()-48 >= 0 && string[i].toInt()-48 <= 9) || string[i] == '.'){
                v += string[i]
            }
            else if(string[i]=='('){
                if(v!="[" && v!="[-"){
                    result += v+"]"
                    v = "["
                }
                stack.addLast("(")
            }else if(string[i]==')'){
                if(v!="[" && v!="[-"){
                    result += v+"]"
                    v = "["
                }
                if(stack.size == 0){
                    return "Syntax Error"
                }
                while(stack[stack.size-1]!="("){
                    if(stack.size == 0){
                        return "Syntax Error"
                    }
                    result += stack[stack.size-1]
                    stack.removeLast()
                }
                stack.removeLast()
            }
            else{
                if (v!="[" && v!="[-"){
                    result += v+"]"
                    v="["
                }else if(string[i]=='-' && string[i-1] != ')' &&(i < 0 || string[i-1].toInt()-48 < 0 || string[i-1].toInt()-48 > 9)){
                    v = "[-"
                    continue;

                }

                while(stack.size!=0 && stack[stack.size-1]!="(" && precedence(string[i].toString()) <= precedence(stack[stack.size-1])){
                    result += stack[stack.size-1]
                    stack.removeLast()
                }
                stack.addLast(string[i].toString())
            }
        }
        if(v!="[" && v!="[-"){
            result += v+"]"
            v = "["
        }
        while(stack.size!=0){
            result += stack[stack.size-1]
            stack.removeLast()
        }
        return result;
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {


        return when(item.itemId){
            R.id.copy -> {
                val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("result",mainActivityMainBinding.textArea.text)
                clipBoard.setPrimaryClip(clipData)
                true
            }
            R.id.paste -> {
                val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var pasteData:String = ""

                val it = clipBoard.primaryClip?.getItemAt(0)
                pasteData = it?.text.toString()

                if (pasteData!=null){
                    val ref = "0123456789+-*/.()"
                    for(i in pasteData) {
                        if (!(i in ref)){
                            mainActivityMainBinding.textArea.text = "Syntax Error"
                            infixString = ""
                        }
                    }
                    mainActivityMainBinding.textArea.text = pasteData
                    infixString = pasteData
                }
                true
            }

            else -> {super.onContextItemSelected(item)}
        }
    }
}
