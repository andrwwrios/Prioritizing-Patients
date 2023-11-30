// Andrew Rios
// 11/03/2023
 
// This class helps medical workers prioritize patients by 
// taking in user input, and based on the input each patient is given a score. 
// Priority is given to the highest score
import java.util.*;
public class PatientPrioritizer {
public static final int HOSPITAL_ZIP = 12345;
	
  // this is the main method which is used to call and run other methods made in the class
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int totalPatients = 0;
		int maxScore = 0;
	
		introduction(); // METHOD 1
		String name = patientName(scanner); //METHOD 2
	
		// while loop runs until "quit' is entered as a name
		while (!name.equals("quit")) {
			int score = patientInfo(scanner); // METHOD 3
			maxScore = Math.max(maxScore, score); //calculates and sets max score
			System.out.println();
			patientsPriority(name,score); // METHOD 5
			System.out.println();
			thankYou(); // 
			totalPatients ++; // when printed after the loop, will show total patients
			name = patientName(scanner); 
		}
		dayStats(totalPatients, maxScore); //METHOD 6
	}	
	
    // Determines if the given integer has five digits.
    // Parameters:
    //   - val: the integer whose digits will be counted
    // Returns:
    //   - boolean: true if the given integer has 5 digits, and false otherwise.
    public static boolean fiveDigits(int val) { // METHOD 7
        val = val / 10000; // get first digit
        if (val == 0) { // has less than 5 digits
            return false;
        } else if (val / 10 == 0) { // has 5 digits
            return true;
        } else { // has more than 5 digits
            return false; 
        }
    }
    
    // This method has no return value and no parameters, and prints out an introduction
    // message to the medical worker
    public static void introduction() { 
    	System.out.println("Hello! We value you and your time, so we will help");
    	System.out.println("you prioritize which patients to see next!");
    	System.out.println("Please answer the following questions about the next patient so");
    	System.out.println("we can help you do your best work :)");
    	System.out.println();
    }
    
    // this method prints out a prompt for the user to enter the 
    // name and returns the patients name
    // parameters: Scanner object sc
    public static String patientName(Scanner scanner) { 
    	System.out.println("Please enter the next patient's name or \"quit\" to end the program.");
    	System.out.print("Patient's name: ");
    	String name = scanner.next();
    	return name;
    }
    
    // this method collects the required patient information and returns the computed score
    // parameters: Scanner object sc
    public static int patientInfo(Scanner scanner) {
    	System.out.print("Patient age: "); // AGE
    	int age = scanner.nextInt();
    
    	System.out.print("Patient zip code: "); // ZIPCODE
    	int zipCode = scanner.nextInt();
    	fiveDigits(zipCode); // checks if the zip code is correct
    	// loop to iterate until user enters valid 5 digit zip code
    	while (!fiveDigits(zipCode)) {
    		System.out.print("Invalid zip code, enter valid zip code: ");
    		zipCode = scanner.nextInt();
    	}
    	
    	System.out.print("Is our hospital \"in network\" for the patient's insurance? ");
    	String insuranceInfo = scanner.next(); // INSURANCE INFO
    	
    	System.out.print("Patient pain level (1-10): "); // PAIN LEVEL
    	int painLevel = scanner.nextInt();
    	isCorrectPainLevel(painLevel);
    	// loop to iterate until user enters valid pain level (1-10)
    	while(isCorrectPainLevel(painLevel) == false) { 
    		System.out.print("Invalid pain level, enter valid pain level (1-10): ");
    		painLevel = scanner.nextInt();
    	}
    	System.out.print("Patient temperature (in degrees Fahrenheit): "); // TEMPERATURE
    	double temperature = scanner.nextDouble();
    	
    	return priorityScore(age, zipCode, insuranceInfo, painLevel, temperature); // returns score
    }
    
    // this method determines whether the user entered a correct pain level (1-10)
    // it returns a boolean which makes writing a loop for this method easier
    // parameters: user pain level
    public static boolean isCorrectPainLevel(int painLevel) {
    	if(painLevel < 1 || painLevel > 10) {
    		return false;
    	}
    	return true;
    }
    
    // this method calculates the patients priority score and returns the score
    // parameters: patients age, zip code, insurance information, pain level, and temperature
    public static int priorityScore(int age, int zipCode, String insuranceInfo, 
      int painLevel, double temperature) { 
    	
    	int score = 100; // all patients start out with a score of 100
    	int firstDigit = zipCode / 10000; // gets first digit of users zip code
    	
    	if(age<12 || age>=75) {
    		score +=50;
    	} 
		  if (firstDigit == (HOSPITAL_ZIP/10000)) { // checks if first digits match
    		score += 25;
    		// only computes second digit if firstDigit == hospital zip code
    		int secondDigit = (zipCode / 1000) % 10; 
    		if(secondDigit == ((HOSPITAL_ZIP/1000) % 10)){
    			score += 15;
          }
    	  } 
		if (insuranceInfo.equals("yes") || insuranceInfo.equals("y")) {
    		score += 50;
    	} 
		if (painLevel < 7) {
    		score = score + painLevel + 10;
    	} else {
    		score = score + painLevel + 70;
    	} 
		if (temperature > 99.5) {
    		score +=8;
    	}  		
    	return score; 
    }
    
    // this method prints out 3 different messages based on the patients priority score
    // high, medium, and low priority
    // parameters: patients name, and priority score from method 4
    public static void patientsPriority(String name, int score) { // METHOD 5
    	if (score >= 277) { // high priority
    		System.out.println("We have found patient " + name + " to have a priority score of: " +
    				score);
    		System.out.println("We have determined this patient is high priority,");
    		System.out.println("and it is advised to call an appropriate medical provider ASAP.");   		
    	} else if (score >= 162) { // medium priority
    		System.out.println("We have found patient " + name + " to have a priority score of: " +
    				score);
    		System.out.println("We have determined this patient is medium priority.");
    		System.out.println("Please assign an appropriate medical provider to their case");
    		System.out.println("and check back in with the patient's condition "
    				+ "in a little while.");
    	} else { // low priority
    		System.out.println("We have found patient " + name + " to have a priority score of: " +
    				score);
    		System.out.println("We have determined this patient is low priority.");
    		System.out.println("Please put them on the waitlist for when a medical "
    				+ "provider becomes available.");
    	}
    }
    
    // this method prints out the overall stats for the day
    // parameters: total number of patients, and highest score
    public static void dayStats(int numPatients, int maxScore) { // METHOD 6
    	System.out.println("Statistics for the day:");
    	System.out.println("..." + numPatients + " patients were helped");
    	System.out.println("...the highest priority patient we saw had a score of " + maxScore);
    	System.out.println("Good job today!");
    }
    
    // this method has no return values or parameters. 
    // It prints out a thank you message after the patient priority is printed
    public static void thankYou() { 
    	System.out.println("Thank you for using our system!");
    	System.out.println("We hope we have helped you do your best!");
    	System.out.println();
    } 
}
