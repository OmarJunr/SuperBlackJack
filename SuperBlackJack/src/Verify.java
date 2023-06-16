public class Verify {

	private int[] playerCardValue;
	private int[] playerCardNum;
	private int playerTotalValue;
	private int[] dealerCardValue;
	private int[] dealerCardNum;
	private int dealerTotalValue;
	
	public Verify(int[] playerCardValue, int[] playerCardNum, int[] dealerCardValue, int[] dealerCardNum){
	    this.playerCardValue = playerCardValue;
	    this.playerCardNum = playerCardNum;
	    this.dealerCardValue = dealerCardValue;
	    this.dealerCardNum = dealerCardNum;
	}
	
	public int playerTotalValue() {
	    playerTotalValue = playerCardValue[1] + playerCardValue[2] + playerCardValue[3] + playerCardValue[4] + playerCardValue[5];
	    if(playerTotalValue > 21) {
	        adjustPlayerAceValue();
	    }
	    playerTotalValue = playerCardValue[1] + playerCardValue[2] + playerCardValue[3] + playerCardValue[4] + playerCardValue[5];
	    return playerTotalValue;
	}
	
	public int dealerTotalValue() {
	    dealerTotalValue = dealerCardValue[1] + dealerCardValue[2] + dealerCardValue[3] + dealerCardValue[4] + dealerCardValue[5];
	    if(dealerTotalValue > 21) {
	        adjustDealerAceValue();
	    }
	    dealerTotalValue = dealerCardValue[1] + dealerCardValue[2] + dealerCardValue[3] + dealerCardValue[4] + dealerCardValue[5];
	    return dealerTotalValue;
	}
	
	public void adjustPlayerAceValue() {
	    for(int i=1; i<6; i++) {
	        if(playerCardNum[i]==1) {
	            playerCardValue[i]=1;
	            playerTotalValue = playerCardValue[1] + playerCardValue[2] + playerCardValue[3] + playerCardValue[4] + playerCardValue[5];
	            if(playerTotalValue < 21) {
	                break;
	            }
	        }
	    }
	}
	
	public void adjustDealerAceValue() {
	    for(int i=1; i<6; i++) {
	        if(dealerCardNum[i]==1) {
	            dealerCardValue[i]=1;
	            dealerTotalValue = dealerCardValue[1] + dealerCardValue[2] + dealerCardValue[3] + dealerCardValue[4] + dealerCardValue[5];
	            if(dealerTotalValue < 21) {
	                break;
	            }
	        }
	    }
	}
}