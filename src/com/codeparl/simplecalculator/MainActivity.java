package com.codeparl.simplecalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	private boolean isAnswer = false;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		
	}//end method 

	public void getButtonValue(View view) {
		
		 final  Button[] mathButtons   =  {
					(Button) findViewById(R.id.dividebtn),
					(Button) findViewById(R.id.timesbtn),
					 (Button) findViewById(R.id.plusbtn),
					 (Button) findViewById(R.id.minusbtn),
					 (Button) findViewById(R.id.powerbtn)
			};
		
		
	final TextView  answerTextView  =  (TextView)  findViewById(R.id.answerTextView);	
		Button  clickedButton  =  (Button) view;
		isAnswer =  false;
		String buttonText  =  clickedButton.getText().toString().trim();
		
		
		
		
		//determine which value to enter in the answer box
		if(buttonText.matches("([\\d|\\/|\\*|\\-|\\+|\\^|\\.]){1}")) {
			
			// and add the value of the button with the value of the 
			//answer box
			String ansValue = answerTextView.getText().toString();
			ansValue +=clickedButton.getText().toString();
			
			//remove the leading zero if available
			if(ansValue.matches("(0.+)"))
				ansValue =  ansValue.substring(1, ansValue.length());
				
			answerTextView.setText(ansValue);
			operatorAlreadyUsed(answerTextView,  mathButtons );;	
			
		
		}//end if
		
		if(buttonText.equals("SQR")) {
			squareRootOf(answerTextView);
		}
		
		// display the answer of the operation
		if(buttonText.equals("=")) {
			String ansValue =  answerTextView.getText().toString();
			if(ansValue.matches("(((\\d+)|(\\d+.?\\d+))[/|\\*|\\-|\\+|\\^]((\\d+)|(\\d+.?\\d+)))")){
				answerTextView.setText(compute(extractExpressions(ansValue)));	
				operatorAlreadyUsed(answerTextView,  mathButtons );	
				
				double  d  =  Double.valueOf(answerTextView.getText().toString());
					if(d - ((int) d) == 0.0)
						answerTextView.setText(String.valueOf(((int) d)));
			}//end if
		}//end if
		
		

		//user can delete all the input in the text box
		if(buttonText.equals("DELETE")) {
			delete(answerTextView);
			shouldDisableBtn( true,mathButtons);
		}
			
		
		
		//user can clear one character at a from the text box
				if(buttonText.equals("C")) {
					 operatorAlreadyUsed(answerTextView,  mathButtons );
					clear(answerTextView);	
				}
					
				
	}//end method 
	
	private void delete(TextView  answerTextView) {
		answerTextView.setText("0");
	}//end method 
	
	private void clear(TextView  answerTextView) {
		String text =  answerTextView.getText().toString();
		if(text.trim().length() > 1) 
			text =  text.substring(0, text.length() - 1);
		else 
			text =  "0";
		   answerTextView.setText(text);
	}//end method 
	
	
	private void shouldDisableBtn( boolean enabled, Button[] mathButtons) {
	for(int i= 0; i < mathButtons.length ; ++i)
		mathButtons[i].setEnabled(enabled);	
	}//end method
	
	
	private void operatorAlreadyUsed(TextView answerTextView, Button[] mathButtons ) {
		String ansValue =  answerTextView.getText().toString();
		boolean operatorAlreadyUsed =  ansValue.matches("(.+?[/|\\*|\\-|\\+|\\^].+?)");
		if(operatorAlreadyUsed)
			shouldDisableBtn( false,mathButtons);
		else 
		shouldDisableBtn( true,mathButtons);	
	}
	
	
	private Map<String, String> extractExpressions(String input){
		Map<String, String>	 expressions =  new HashMap<String, String>();
		Pattern  patern  = Pattern.compile("[/|\\*|\\-|\\+|\\^]");
		Matcher   math  =  patern.matcher(input);
		if(math.find()) {
			int index  =  math.start();
			expressions.put("left", input.substring(0,index));
			expressions.put("right", input.substring(index+1,input.length()));
			expressions.put("symbol", String.valueOf(input.charAt(index)));
		}
		return expressions;
	}//end method 
	
	
	
	//These method does the operation of the calculation
	private  String compute(Map<String, String> expression ) {
		
		char  symbol  = expression.get("symbol").charAt(0);
		String left  =  expression.get("left");
		String right  =  expression.get("right");
		
		switch(symbol) {
		
		case '/':
		return String.format("%s",Double.valueOf(left) / Double.valueOf(right)   );
		case '*':
			return String.format("%s",Double.valueOf(left) * Double.valueOf(right)   );
		case '-':
			return String.format("%s",Double.valueOf(left) - Double.valueOf(right)   );
		case '+':
			return String.format("%s",Double.valueOf(left) + Double.valueOf(right)   );
		case '^':
	        return String.format("%s", Math.pow(Double.valueOf(left),  Double.valueOf(right))+"");
		
		}
		
		return "";
	} //end method 

	private void squareRootOf(TextView  answerTextView) {
		String text  =  answerTextView.getText().toString().trim();
		if(!text.matches("(.+?[/|\\*|\\-|\\+|\\^].+?)") && text.matches("(\\d+)") ) {
			double  root  =  Double.valueOf(text);
			answerTextView.setText(""+Math.sqrt(root));	
		}
		
	}
	
}//end class 
