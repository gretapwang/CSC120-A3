
import java.util.Scanner;
import java.util.Random;

class Conversation implements ConversationRequirements {

  // Attributes 
  String[] transcript;

  /**
   * Constructor 
   */
  Conversation() {
    this.transcript = new String[0];
  }

  /**
   * Starts and runs the conversation with the user
   */
  public void chat() {
    Scanner input = new Scanner(System.in);

    // Ask how many rounds and set appropriate transcript length
    System.out.print("How many rounds? ");
    int rounds = input.nextInt();
    input.nextLine();
    this.transcript = new String[rounds*2 + 2];

    // Print greeting
    System.out.println("Hi there!  What's on your mind?");
    this.transcript[0] = "Hi there!  What's on your mind?";
    
    // Carry out conversation
    String userStatement = "";
    for (int i = 1; i < rounds*2; i += 2){
      userStatement = input.nextLine();
      this.transcript[i] = userStatement;
      String response = respond(userStatement);
      System.out.println(response);
      this.transcript[i+1] = response;
    }
    input.close();

    // Print goodbye
    System.out.println("See ya!");
    this.transcript[rounds*2 + 1] = "See ya!";
  }

  /**
   * Prints transcript of conversation
   */
  public void printTranscript() {
    System.out.println("\nTRANSCRIPT:");
    for (int i = 0; i < this.transcript.length; i++){
      System.out.println(this.transcript[i]);
    }
  }

  /**
   * Gives appropriate response (mirrored or canned) to user input
   * @param inputString the users last line of input
   * @return mirrored or canned response to user input  
   */
  public String respond(String inputString) {
    // Remove punctuation from end of input, if present
    String lastChar = inputString.substring(inputString.length() - 1, inputString.length());
    if (lastChar.equals(".") || lastChar.equals("!") || lastChar.equals("?")){
      inputString = inputString.substring(0, inputString.length() - 1);
    }

    // Split input into Array of words, mirror each word if needed
    String[] words = inputString.split(" ");
    int nonMirrorWords = 0;
    for (int i = 0; i < words.length; i++){
      if (words[i].equals("I")){
        if (i == 0){
          words[i] = "You";
        }
        else {
          words[i] = "you";
        }
      }
      else if (words[i].equals("me")){
        words[i] = "you";
      }
      else if (words[i].equals("Me")){
        words[i] = "You";
      }
      else if (words[i].equals("am")){
        words[i] = "are";
      }
      else if (words[i].equals("Am")){
        words[i] = "Are";
      }
      else if (words[i].equals("are")){
        words[i] = "am";
      }
      else if (words[i].equals("Are")){
        words[i] = "Am";
      }
      else if (words[i].equals("you") || words[i].equals("You")){
        words[i] = "I";
      }
      else if (words[i].equals("my")){
        words[i] = "your";
      }
      else if (words[i].equals("My")){
        words[i] = "Your";
      }
      else if (words[i].equals("your")){
        words[i] = "my";
      }
      else if (words[i].equals("Your")){
        words[i] = "My";
      }
      else if (words[i].equals("you're") || words[i].equals("You're")){
        words[i] = "I'm";
      }
      else if (words[i].equals("I'm")){
        if (i == 0){
          words[i] = "You're";
        }
        else {
          words[i] = "you're";
        }
      }
      else {
        // Count number of words that are not mirrored
        nonMirrorWords += 1;
      }
    }

    String returnString = "";
    // If no words were mirrored, choose canned response
    if (nonMirrorWords == words.length){
      String[] cannedResponses = {"Mmm-hm.", "Cool!", "Uh huh.", "I see.", "Gotcha."};
      Random random = new Random();
      returnString = cannedResponses[random.nextInt(cannedResponses.length)];
    }
    // If words were mirrored, concatenate Array elements back into a String and add question mark
    else {
      for (int i = 0; i < words.length; i++){
        returnString = returnString.concat(words[i] + " ");
      }
      returnString = returnString.substring(0, returnString.length() - 1); // Remove extra space from end
      returnString = returnString.concat("?");
    }
    return returnString; 
  }

  public static void main(String[] arguments) {

    Conversation myConversation = new Conversation();
    myConversation.chat();
    myConversation.printTranscript();

  }
}
