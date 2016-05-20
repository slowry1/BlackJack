package cpp.scottl.com.blackjack;

import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView  dealer1;
    private ImageView  dealer2;
    private ImageView  dealer3;
    private ImageView  dealer4;
    private ImageView  dealer5;
    private ImageView  user1;
    private ImageView  user2;
    private ImageView  user3;
    private ImageView  user4;
    private ImageView  user5;
    private TextView playerHandTV, dealerHandTV;
    private Button hit;
    private Button stand;
    private Drawable draw;
    private int playerHand;
    private int dealerHand;
    private boolean dealersTurn = false;
    private boolean playersTurn = true;
    ArrayList<HashMap<Integer, Integer>> arrayList;
    HashMap<Integer, Integer> hashMap;
    Random random;
    int dealerValue = 0;
    int playerValue = 0;
    int[] currentCardArray;
    int[] dealerStartingCards;
    Drawable newCard;
    int[] playerImageView = {R.id.ivp3, R.id.ivp4, R.id.ivp5};
    int[] dealerImageView = {R.id.iv3, R.id.iv4, R.id.iv5};
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerHand = 0;
        dealerHand = 0;

        dealer1= (ImageView) findViewById(R.id.iv1);
        dealer2= (ImageView) findViewById((R.id.iv2));
        draw = getResources().getDrawable(R.drawable.yugi);
        dealer1.setImageDrawable(draw);
        hit= (Button) findViewById(R.id.hit);
        stand= (Button) findViewById(R.id.stand);
        playerHandTV = (TextView) findViewById(R.id.playerHandTotal);
        dealerHandTV = (TextView) findViewById(R.id.dealerHandTotal);
        random = new Random();

        popDeck();
        // dealer first turn
        dealersTurn = true;
        playersTurn = false;
        dealerStartingCards = new int[2];
        currentCardArray = getRandomCard();
        dealerStartingCards[0]= currentCardArray[1];
        dealerValue += currentCardArray[0];
        currentCardArray = getRandomCard();
        dealerStartingCards[1]= currentCardArray[1];
        dealerValue += currentCardArray[0];
        dealerHandTV.setText(String.valueOf(currentCardArray[0]));
        newCard = getResources().getDrawable(currentCardArray[1]);
        dealer2.setImageDrawable(newCard);

        // done with dealer starting cards

        //Start of players first turn
        dealersTurn = false;
        playersTurn = true;
        currentCardArray = getRandomCard();
        //Need loop for dealer and player and bool to check whos turn it is.
        newCard = getResources().getDrawable(currentCardArray[1]);
        ImageView ivp1 = (ImageView) findViewById(R.id.ivp1);
        ivp1.setImageDrawable(newCard);
        playerValue += currentCardArray[0];

        currentCardArray = getRandomCard();
        //Need loop for dealer and player and bool to check whos turn it is.
        newCard = getResources().getDrawable(currentCardArray[1]);
        ImageView ivp2 = (ImageView) findViewById(R.id.ivp2);
        ivp2.setImageDrawable(newCard);
        playerValue += currentCardArray[0];
        playerHandTV.setText(String.valueOf(playerValue));
        //end players first turn
        if(playerValue == 21){
            hit.setEnabled(false);
            dealersTurn = true;
            playersTurn = false;
            startDealersTurn();
        }


        hit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(counter<3) {
                    // Perform action on click
                    currentCardArray = getRandomCard();
                    newCard = getResources().getDrawable(currentCardArray[1]);
                    ImageView tempIV = (ImageView) findViewById(playerImageView[counter]);
                    tempIV.setImageDrawable(newCard);
                    playerValue += currentCardArray[0];
                    playerHandTV.setText(String.valueOf(playerValue));
                    counter++;
                }else{
                    Toast.makeText(MainActivity.this, "Hand is full. Hit STAND.", Toast.LENGTH_SHORT).show();
                    hit.setEnabled(false);
                }
                if(playerValue >21) {
                    Toast.makeText(MainActivity.this, "Bust! You lose!", Toast.LENGTH_LONG).show();
                    hit.setEnabled(false);
                    stand.setEnabled(false);
                }
                else if(playerValue == 21){
                    hit.setEnabled(false);
                    dealersTurn = true;
                    playersTurn = false;
                    startDealersTurn();
                }

            }
        });

        stand.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                dealersTurn = true;
                playersTurn = false;
                hit.setEnabled(false);
                startDealersTurn();
            }
        });

    }

    private void startDealersTurn() {
        int counter = 0;
        dealerHandTV.setText(String.valueOf(dealerValue));
        newCard = getResources().getDrawable(dealerStartingCards[0]);
        dealer1.setImageDrawable(newCard);
        if(playerValue == dealerValue)
            Toast.makeText(MainActivity.this, "PUSH, Both have 21", Toast.LENGTH_SHORT).show();
        else{
            while(dealerValue<=17&&counter<3){
                currentCardArray = getRandomCard();
                newCard = getResources().getDrawable(currentCardArray[1]);
                ImageView tempIV = (ImageView) findViewById(dealerImageView[counter]);
                tempIV.setImageDrawable(newCard);
                dealerValue += currentCardArray[0];
                dealerHandTV.setText(String.valueOf(dealerValue));
                counter++;
            }
        }

        if(counter<3) {
            // Perform action on click
            currentCardArray = getRandomCard();
            newCard = getResources().getDrawable(currentCardArray[1]);
            ImageView tempIV = (ImageView) findViewById(playerImageView[counter]);
            tempIV.setImageDrawable(newCard);
            playerValue += currentCardArray[0];
            playerHandTV.setText(String.valueOf(playerValue));
            counter++;
        }else{
            Toast.makeText(MainActivity.this, "Hand is full. Hit STAND.", Toast.LENGTH_SHORT).show();
            hit.setEnabled(false);
        }

/*
        currentCardArray = getRandomCard();
        //Need loop for dealer and player and bool to check whos turn it is.
        newCard = getResources().getDrawable(currentCardArray[1]);
        ImageView ivp1 = (ImageView) findViewById(R.id.ivp1);
        ivp1.setImageDrawable(newCard);
        playerValue += currentCardArray[0];

        currentCardArray = getRandomCard();
        //Need loop for dealer and player and bool to check whos turn it is.
        newCard = getResources().getDrawable(currentCardArray[1]);
        ImageView ivp2 = (ImageView) findViewById(R.id.ivp2);
        ivp2.setImageDrawable(newCard);
        playerValue += currentCardArray[0];
        playerHandTV.setText(String.valueOf(playerValue));*/


    }

    public void popDeck() {
        arrayList = new ArrayList<>();

        //2
        hashMap = new HashMap<>();
        hashMap.put(0,R.drawable.to_of_clubs);
        hashMap.put(1,R.drawable.to_of_diamonds);
        hashMap.put(2,R.drawable.to_of_hearts);
        hashMap.put(3,R.drawable.to_of_spades);
        arrayList.add(hashMap);
//3
        hashMap = new HashMap<>();
        hashMap.put(0,R.drawable.th_of_clubs);
        hashMap.put(1,R.drawable.th_of_diamonds);
        hashMap.put(2,R.drawable.th_of_hearts);
        hashMap.put(3,R.drawable.th_of_spades);
        arrayList.add(hashMap);

   //4
        hashMap = new HashMap<>();
        hashMap.put(0,R.drawable.fo_of_clubs);
        hashMap.put(1,R.drawable.fo_of_diamonds);
        hashMap.put(2,R.drawable.fo_of_hearts);
        hashMap.put(3,R.drawable.fo_of_spades);
        arrayList.add(hashMap);

//5
        hashMap = new HashMap<>();
        hashMap.put(0,R.drawable.fi_of_clubs);
        hashMap.put(1,R.drawable.fi_of_diamonds);
        hashMap.put(2,R.drawable.fi_of_hearts);
        hashMap.put(3,R.drawable.fi_of_spades);
        arrayList.add(hashMap);
//6

        hashMap = new HashMap<>();
        hashMap.put(0,R.drawable.si_of_clubs);
        hashMap.put(1,R.drawable.si_of_diamonds);
        hashMap.put(2,R.drawable.si_of_hearts);
        hashMap.put(3,R.drawable.si_of_spades);
        arrayList.add(hashMap);

//7
        hashMap = new HashMap<>();
        hashMap.put(0,R.drawable.se_of_clubs);
        hashMap.put(1,R.drawable.se_of_diamonds);
        hashMap.put(2,R.drawable.se_of_hearts);
        hashMap.put(3,R.drawable.se_of_spades);
        arrayList.add(hashMap);

//8
        hashMap = new HashMap<>();
        hashMap.put(0,R.drawable.e_of_hearts);
        hashMap.put(1,R.drawable.e_of_spades);
        hashMap.put(2,R.drawable.e_of_diamonds);
        hashMap.put(3,R.drawable.e_of_clubs);
        arrayList.add(hashMap);

//9
        hashMap = new HashMap<>();
        hashMap.put(0,R.drawable.n_of_clubs);
        hashMap.put(1,R.drawable.n_of_diamonds);
        hashMap.put(2,R.drawable.n_of_hearts);
        hashMap.put(3,R.drawable.n_of_spades);
        arrayList.add(hashMap);

//10,J,Q,K
        hashMap = new HashMap<>();
        hashMap.put(0,R.drawable.te_of_clubs);
        hashMap.put(1,R.drawable.te_of_diamonds);
        hashMap.put(2,R.drawable.te_of_hearts);
        hashMap.put(3,R.drawable.te_of_spades);
        hashMap.put(4,R.drawable.jack_of_clubs2);
        hashMap.put(5,R.drawable.jack_of_diamonds2);
        hashMap.put(6,R.drawable.jack_of_hearts2);
        hashMap.put(7,R.drawable.jack_of_spades2);
        hashMap.put(8,R.drawable.queen_of_clubs2);
        hashMap.put(9,R.drawable.queen_of_diamonds2);
        hashMap.put(10,R.drawable.queen_of_hearts2);
        hashMap.put(11, R.drawable.queen_of_spades2);
        hashMap.put(12,R.drawable.king_of_clubs2);
        hashMap.put(13,R.drawable.king_of_diamonds2);
        hashMap.put(14,R.drawable.king_of_hearts2);
        hashMap.put(15,R.drawable.king_of_spades2);
        arrayList.add(hashMap);

//A
        hashMap = new HashMap<>();
        hashMap.put(0,R.drawable.ace_of_clubs);
        hashMap.put(1,R.drawable.ace_of_diamonds);
        hashMap.put(2,R.drawable.ace_of_hearts);
        hashMap.put(3,R.drawable.ace_of_spades);
        arrayList.add(hashMap);
    }

    int[] getRandomCard() {
        int[] array = new int[2];
        int cardNumber = random.nextInt(4) ;
        if(!arrayList.get(cardNumber).isEmpty()){
            HashMap<Integer,Integer> cardHM= arrayList.get(cardNumber);
            cardNumber +=2;
            if(dealersTurn) {
                dealerValue += cardNumber;
                dealerHandTV.setText(String.valueOf(dealerValue));
            }

            int hmNumber = random.nextInt(cardHM.size());
            while(!cardHM.containsKey(hmNumber))
                hmNumber = random.nextInt(cardHM.size());
            int cardImageInt = cardHM.get(hmNumber);
            cardHM.remove(hmNumber);
            // first index is  value of card second is the image int
            array[0]= cardNumber;
            array[1]=cardImageInt;
            return array;
        }
        else
            getRandomCard();
        return null;
    }

}
