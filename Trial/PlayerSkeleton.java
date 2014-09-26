package Trial;

public class PlayerSkeleton {

	
	double[] score;
	final double odd_divisibility_weight = .5;
	final double even_divisibility_weight =.5;
	final double board_divisibility_weight = 3;
	//implement this function to have a working system
	public int pickMove(State s, int[][] legalMoves) {
		
		featureSelector(s);
		
		for(int i=0; i<number_of_cols; i++){
			
			System.out.print(height[i]+",");
		}
		System.out.println("");
		
		switch(s.getNextPiece()){
		case 0:
			initialize0();
			break;
		case 1:
			initialize1();
			break;
		case 2:
			initialize2();
			break;
		case 3:
			initialize3();
			break;
		case 4:
			initialize4();
			break;
		case 5:
			initialize5();
			break;
		case 6:
			initialize6();
			break;
		}
		diagnostic();
		int selected_move = 0;
		
		double best = Double.MAX_VALUE;
		
		for(int i=0; i<score.length; i++){
			
			if(best> score[i]){
				best = score[i];
				selected_move = i;
			}
		}
		System.out.println("Selected_move: "+selected_move);
		System.out.println("");
		
		return selected_move;
		//return 25;
	}
	//DONE
	private void initialize6() {
		score = new double[17];
		//double big = Math.max(Math.max(height[0], height[1]), height[2]);
		score[0] =number_of_rows -Math.max(height[0], Math.max(height[1], height[2])) < 3? Double.MAX_VALUE/32 :Math.abs(height[1]-height[2]) + 3*Math.abs(1-(height[0] - height[1]))+Math.abs(1-(height[3] - height[2])) + 0.01* number_of_holes[0]+ 0.01* number_of_holes[1]+ 0.01* number_of_holes[2];
		//big = Math.max(Math.max(height[7], height[8]), height[9]);
		score[7] =number_of_rows -Math.max(height[9], Math.max(height[8], height[7])) < 3? Double.MAX_VALUE/32 : Math.abs(height[9]-height[8]) +3*Math.abs(1-(height[7] - height[8]))+Math.abs(1-(height[6] - height[7])) + 0.01* number_of_holes[7]+ 0.01* number_of_holes[8]+ 0.01* number_of_holes[9];//start from right

		for(int i=1; i<7; i++){
			//big = Math.max(Math.max(height[i], height[i+1]), height[i+2]);
			score[i] = number_of_rows -Math.max(height[i], Math.max(height[i+1], height[i+2])) < 3? Double.MAX_VALUE/32 : Math.abs(height[i+2]-height[i+1]) +3*Math.abs(1-(height[i] - height[i+1])) +Math.abs(1-(height[i+3] - height[i+2])) +Math.abs(1-(height[i-1] - height[i])) + 0.01* number_of_holes[i]+ 0.01* number_of_holes[i+1]+ 0.01* number_of_holes[i+2];
		}
		for(int i=0; i<8; i++){
			
			score[i] += 2*Math.max(height[i] , Math.max(height[i+1] ,height[i+2]));
		}
		//big = Math.max(height[0], height[1]);
		score[8] = number_of_rows - Math.max(height[0], height[1]) <4? Double.MAX_VALUE/32 :3* Math.abs(1-(height[1] - height[0])) +Math.abs(2 - (height[2] - height[1])) +0.01*number_of_holes[0];
		//big = Math.max(height[8],height[9]);
		score[16] = number_of_rows - Math.max(height[8], height[9]) <4? Double.MAX_VALUE/32 : 3*Math.abs(1 - (height[9] - height[8]))+Math.abs(2-(height[7]-height[6]))+0.01*number_of_holes[8];
		for(int i =1; i<8;i++){
			//big = Math.max(height[i], height[i+1]);
			score[i+8] = number_of_rows - Math.max(height[i], height[i+1]) <4? Double.MAX_VALUE/32 : 3*Math.abs(1- (height[i+1] - height[i])) +Math.abs(2 -(height[i-1] - height[i])) +Math.abs(2 - (height[i+2] - height[i+1]))+0.01*number_of_holes[i];
		}
		
		for(int i=0; i<9;i++){
			score[i+8] += 2*Math.max(height[i] ,height[i+1]);
		}
	}
	//DONE
	private void initialize5() {
		
		score = new double[17];
		score[0] =number_of_rows -Math.max(height[0], Math.max(height[1], height[2])) < 3? Double.MAX_VALUE/32 :Math.abs(height[0]-height[1]) + 3*Math.abs(1-(height[2] - height[1]))+Math.abs(1-(height[3] - height[2])) + 0.01* number_of_holes[0]+ 0.01* number_of_holes[1]+ 0.01* number_of_holes[2];
		score[7] =number_of_rows -Math.max(height[9], Math.max(height[8], height[7])) < 3? Double.MAX_VALUE/32 :Math.abs(height[7]-height[8]) +3* Math.abs(1-(height[9] - height[8]))+Math.abs(1-(height[6] - height[7])) + 0.01* number_of_holes[7]+ 0.01* number_of_holes[8]+ 0.01* number_of_holes[9]-0.07;//start from right
		
		for(int i=1; i<7; i++){
			score[i] = number_of_rows -Math.max(height[i], Math.max(height[i+1], height[i+2])) < 3? Double.MAX_VALUE/32 :Math.abs(height[i]-height[i+1]) +3*Math.abs(1-(height[i+2] - height[i+1])) +Math.abs(1-(height[i+3] - height[i+2])) +Math.abs(1-(height[i-1] - height[i])) + 0.01* number_of_holes[i]+ 0.01* number_of_holes[i+1]+ 0.01* number_of_holes[i+2] - i*0.01;
		}
		
		for(int i=0; i<8; i++){
			
			score[i] += 2*Math.max(height[i] , Math.max(height[i+1] ,height[i+2]));
		}
		
		score[8] = number_of_rows - Math.max(height[0], height[1]) <4? Double.MAX_VALUE/32 :3* Math.abs(1-(height[0] - height[1])) +Math.abs(2 - (height[2] - height[1])) +0.01*number_of_holes[0];
		score[16] = number_of_rows - Math.max(height[8], height[9]) <4? Double.MAX_VALUE/32 : 3*Math.abs(1 - (height[8] - height[9]))+Math.abs(2-(height[7]-height[8]))+0.01*number_of_holes[8]-0.08;
		
		for(int i =1; i<8;i++){
			score[i+8] = number_of_rows - Math.max(height[i], height[i+1]) <4? Double.MAX_VALUE/32 : 3*Math.abs(1- (height[i] - height[i+1])) +Math.abs(2 -(height[i-1] - height[i])) +Math.abs(2 - (height[i+2] - height[i+1]))+0.01*number_of_holes[i]-0.01*i;
		}
		
		for(int i=0; i<9;i++){
			score[i+8] += 2*Math.max(height[i] ,height[i+1]);
		}
	}
	//DONE
	private void initialize4() {
		score = new double[34];
		
		//0-8
		score[0] = number_of_rows - Math.max(height[0], height[1])<4? Double.MAX_VALUE/32:2*Math.abs(1-(height[1]-height[0]))+Math.abs(1-(height[2] - height[1]))+0.01*number_of_holes[0]+0.01*number_of_holes[1];
		score[8] = number_of_rows - Math.max(height[8], height[9]) <4? Double.MAX_VALUE/32:Math.abs(3-(height[7]-height[8]))+2*Math.abs(1-(height[9] - height[8]))+0.01*number_of_holes[8]+0.01*number_of_holes[9];
		for(int i=1; i<8; i++){
			score[i] = number_of_rows - Math.max(height[i], height[i+1])<4? Double.MAX_VALUE/32: Math.abs(3-(height[i-1]-height[i])) +2* Math.abs(1 - (height[i+1] - height[i])) +Math.abs(1-(height[i+2]-height[i+1]))+0.01*number_of_holes[i]+0.01*number_of_holes[i+1];
		}
		
		for(int i=0; i<9; i++){//choose a lower height
			score[i] +=Math.max(height[i], height[i+1]);
			
			//positional divisibility penalty, if the position is divisible then good( low score) otherwise penalise according to remainder
			//score[i] +=even_divisibility_weight*(flatness[i]%2);
			
			//general divisibilty penalty, this is a bias given by the width of the board
			//score[i] += board_divisibility_weight*even_divisibility_weight*(number_of_cols %2);
		}
		
		//9-16
		score[9] = number_of_rows - Math.max(Math.max(height[0], height[1]), height[2])<3? Double.MAX_VALUE/32:Math.abs(1-(height[0]-height[1]))+Math.abs(1-(height[2]-height[1])) +Math.abs(height[0]-height[2])+Math.abs(1-(height[3] - height[2])) +0.01*number_of_holes[0]+0.01*number_of_holes[1]+0.01*number_of_holes[2];
		score[16] = number_of_rows - Math.max(Math.max(height[7], height[8]), height[9])<3? Double.MAX_VALUE/32:Math.abs(1-(height[6] - height[7]))+Math.abs(1-(height[7]-height[8]))+Math.abs(1 - (height[9] - height[8]))+Math.abs(height[7]+height[9])+0.01*number_of_holes[7]+0.01*number_of_holes[8]+0.01*number_of_holes[9];
		for(int i=1; i<7; i++){
			score[i+9] = number_of_rows - Math.max(Math.max(height[i], height[i+1]), height[i+2])<3? Double.MAX_VALUE/32:Math.abs(1-(height[i-1]-height[i]))+Math.abs(1-(height[i]-height[i+1]))+Math.abs(1-(height[i+2]-height[i+1]))+Math.abs(height[i]-height[i+2])+Math.abs(1-(height[i+3]-height[i+2]))+0.01*number_of_holes[i]+0.01*number_of_holes[i+1]+0.01*number_of_holes[i+2];
		}
		
		for(int i=0; i<8; i++){
			score[i+9] += Math.max(Math.max(height[i], height[i+1]), height[i+2]);
			
			//positional divisibility penalty, if the position is divisible then good( low score) otherwise penalise according to remainder
			//score[i+9] +=odd_divisibility_weight*(flatness[i]%3);
			
			//general divisibilty penalty, this is a bias given by the width of the board
			//score[i+9] += board_divisibility_weight*odd_divisibility_weight*(number_of_cols %3);
		}
		//17-25
		score[17] = number_of_rows - Math.max(height[0], height[1])<4? Double.MAX_VALUE/32:Math.abs(1-(height[0]-height[1]))+Math.abs(3-(height[2] - height[1]))+0.01*number_of_holes[0]+0.01*number_of_holes[1];
		score[25] = number_of_rows - Math.max(height[8], height[9]) <4? Double.MAX_VALUE/32:Math.abs(1-(height[7]-height[8]))+2*Math.abs(1-(height[8] - height[9]))+0.01*number_of_holes[8]+0.01*number_of_holes[9];
		for(int i=1; i<8; i++){
			score[i+17] = number_of_rows - Math.max(height[i], height[i+1])<4? Double.MAX_VALUE/32: Math.abs(1-(height[i-1]-height[i])) + 2*Math.abs(1 - (height[i] - height[i+1])) +Math.abs(3-(height[i+2]-height[i+1]))+0.01*number_of_holes[i]+0.01*number_of_holes[i+1];
		}
		
		for(int i=0; i<9; i++){
			score[i+17] +=Math.max(height[i], height[i+1]);
			
			//positional divisibility penalty, if the position is divisible then good( low score) otherwise penalise according to remainder
			//score[i+17] +=even_divisibility_weight*(flatness[i]%2);
			
			//general divisibilty penalty, this is a bias given by the width of the board
			//score[i+17] += board_divisibility_weight*even_divisibility_weight*(number_of_cols %2);
		}
		//26-33
		score[26] = number_of_rows - Math.max(Math.max(height[0], height[1]), height[2])<3? Double.MAX_VALUE/32:Math.abs(height[0] - height[1])+Math.abs(height[1] - height[2])+Math.abs(height[0] - height[2])+Math.abs(1-(height[3]-height[2]))+0.01*number_of_holes[0]+0.01*number_of_holes[1]+0.01*number_of_holes[2];
		score[33] = number_of_rows - Math.max(Math.max(height[7], height[8]), height[9])<3? Double.MAX_VALUE/32:Math.abs(1-(height[6]-height[7]))+Math.abs(height[7]-height[8])+Math.abs(height[8]-height[9])+Math.abs(height[7]-height[9])+0.01*number_of_holes[7]+0.01*number_of_holes[8]+0.01*number_of_holes[9];
		for(int i=1; i<7; i++){
			score[i+26] = number_of_rows - Math.max(Math.max(height[i], height[i+1]), height[i+2])<3? Double.MAX_VALUE/32:Math.abs(1-(height[i-1]-height[i]))+Math.abs(height[i]-height[i+1])+Math.abs(height[i]-height[i+2])+Math.abs(height[i+1]-height[i+2])+Math.abs(1-(height[i+3]-height[i+2]))+0.01*number_of_holes[i]+0.01*number_of_holes[i+1]+0.01*number_of_holes[i+2];
		}
		
		for(int i=0; i<8; i++){
			score[i+26] += Math.max(Math.max(height[i], height[i+1]), height[i+2]);
			
			//positional divisibility penalty, if the position is divisible then good( low score) otherwise penalise according to remainder
			//score[i+26] +=odd_divisibility_weight*(flatness[i]%3);
			
			//general divisibilty penalty, this is a bias given by the width of the board
			//score[i+26] += board_divisibility_weight*odd_divisibility_weight*(number_of_cols %3);
		}
	}
	//DONE
	private void initialize3() {
		score = new double[34];
		//0-8
		for(int i=0; i<9; i++){
			
			score[i] =number_of_rows -Math.max(height[i], height[i+1])<4? Double.MAX_VALUE/32: Math.abs(height[i] - height[i+1]);
		}
		score[0] += Math.abs(3 - (height[2] - Math.max(height[0],height[1]))) + 0.01*number_of_holes[0] + 0.01*number_of_holes[1]+0.01*number_of_holes[0]+0.05;
		score[8] += Math.abs(1 - (height[7] - Math.max(height[8], height[9]))) + 0.01*number_of_holes[9]+ 0.01*number_of_holes[8] +0.01*number_of_holes[9]+0.05;
		for(int i=1 ; i<8; i++){
			
			score[i] += Math.abs(1 - (height[i-1] - Math.max(height[i+1], height[i]))) + Math.abs(3 - (height[i+2] - Math.max(height[i+1], height[i]))) + 0.01*number_of_holes[i]+ 0.01*number_of_holes[i+1]+0.05;
		}
		for(int i=0; i<9; i++){//choose a lower height
			score[i] +=2*Math.max(height[i], height[i+1]);
			
			//positional divisibility penalty, if the position is divisible then good( low score) otherwise penalise according to remainder
			score[i] +=even_divisibility_weight*(flatness[i]%2);
			
			//general divisibilty penalty, this is a bias given by the width of the board
			score[i] += board_divisibility_weight*even_divisibility_weight*(number_of_cols %2);
		}
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//9-16
		score[9] = number_of_rows - Math.max(Math.max(height[0], height[1]), height[2]) <3? Double.MAX_VALUE/32 : Math.abs(height[1] -height[0]) +Math.abs(height[2]-height[0])+Math.abs(height[1] - height[2]) + Math.abs(1-(height[3] - height[2])) +0.01*number_of_holes[0]+0.01*number_of_holes[1]+0.01*number_of_holes[2];
		score[16] = number_of_rows - Math.max(Math.max(height[7], height[8]), height[9]) <3? Double.MAX_VALUE/32: Math.abs(height[8] - height[7]) +Math.abs(height[9] - height[7]) +Math.abs(height[8] - height[9]) +Math.abs(height[6] - height[7]) +0.01*number_of_holes[7]+0.01*number_of_holes[8]+0.01*number_of_holes[9];
		
		for(int i=1; i<8; i++){
			
			score[i+9] = number_of_rows - Math.max(Math.max(height[i], height[i+1]), height[i+2]) <3? Double.MAX_VALUE/32 : Math.abs(height[i+1] - height[i])+Math.abs(height[i+2] - height[i]) +Math.abs(height[i+1]-height[i+2]) +Math.abs(1-(height[i-1] - height[i+1]))+0.01*number_of_holes[i]+0.01*number_of_holes[i+1]+0.01*number_of_holes[i+2];
		}
		
		for(int i=0; i<8; i++){
			score[i+9] += 2.*Math.max(Math.max(height[i], height[i+1]), height[i+2]);
			
			//positional divisibility penalty, if the position is divisible then good( low score) otherwise penalise according to remainder
			score[i+9] +=odd_divisibility_weight*(flatness[i]%3);
			
			//general divisibilty penalty, this is a bias given by the width of the board
			score[i+9] += board_divisibility_weight*odd_divisibility_weight*(number_of_cols %3);
		}
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//17-25
		score[17] = number_of_rows - Math.max(height[0], height[1])<4? Double.MAX_VALUE/32 : 1.5*Math.abs(2 - (height[1] - height[0])) +Math.abs(1 - (height[2] -height[1])) + 0.01*number_of_holes[0] +0.01 * number_of_holes[1];
		score[25] = number_of_rows - Math.max(height[8], height[9])<4? Double.MAX_VALUE/32: 1.5*Math.abs(2 - (height[9] - height[8])) + Math.abs(3 - (height[7] - height[8]))+ 0.01*number_of_holes[8] +0.01 * number_of_holes[9];
		
		for(int i=1; i<8; i++){
			score[i+17] = number_of_rows - Math.max(height[i], height[i+1]) <4? Double.MAX_VALUE/32 :1.5*Math.abs(2 - (height[i+1] - height[i])) +Math.abs(3 - (height[i-1] - height[i])) +Math.abs(1 - (height[i+2] - height[i+1]));
			
		}
		
		for(int i=0; i<9; i++){
			score[i+17] += 2*Math.max(height[i], height[i+1]);
			
			//positional divisibility penalty, if the position is divisible then good( low score) otherwise penalise according to remainder
			score[i+17] +=even_divisibility_weight*(flatness[i]%2);
			
			//general divisibilty penalty, this is a bias given by the width of the board
			score[i+17] += board_divisibility_weight*even_divisibility_weight*(number_of_cols %2);
		}
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//26-33
		score[26] = number_of_rows - Math.max(Math.max(height[0], height[1]), height[2]) <3? Double.MAX_VALUE/32 : Math.abs(2 - (height[3] -height[2] )) + Math.abs(height[0] - height[1])+Math.abs(1-(height[0] - height[2])) +Math.abs(1-(height[1] - height[2]))+ 0.01*number_of_holes[0]+ 0.01*number_of_holes[1]+ 0.01*number_of_holes[2];
		score[33] = number_of_rows - Math.max(Math.max(height[7], height[8]), height[9]) <3? Double.MAX_VALUE/32 : Math.abs(1 - (height[6] - height[7])) + Math.abs(height[7] - height[8]) +Math.abs(1-(height[7] - height[9]))+Math.abs(1-(height[8] - height[9]))+ 0.01*number_of_holes[7]+ 0.01*number_of_holes[8]+ 0.01*number_of_holes[9];
		
		for(int i=1; i<7; i++){
			score[i+26] = number_of_rows - Math.max(Math.max(height[i], height[i+1]), height[i+2]) <3? Double.MAX_VALUE/32 : Math.abs(2 - (height[i+3] - height[i+2])) +Math.abs(1 - (height[i-1] - height[i])) +Math.abs(height[i] - height[i+1]) +Math.abs(1-(height[i] - height[i+2]))+Math.abs(1-(height[i+1] - height[i+2]))+ 0.01*number_of_holes[i]+ 0.01*number_of_holes[i+1]+ 0.01*number_of_holes[i+2];
		}
		
		for(int i=0; i<8; i++){
			score[i+26] += 2.*Math.max(Math.max(height[i], height[i+1]), height[i+2]);
			
			//positional divisibility penalty, if the position is divisible then good( low score) otherwise penalise according to remainder
			score[i+26] +=odd_divisibility_weight*(flatness[i]%3);
			
			//general divisibilty penalty, this is a bias given by the width of the board
			score[i+26] += board_divisibility_weight*odd_divisibility_weight*(number_of_cols %3);
		}
	}
	//DONE
	private void initialize2() {
		score = new double[34];
		//0-8
		for(int i=0; i<9; i++){
			
			score[i] =number_of_rows -Math.max(height[i], height[i+1])<4? Double.MAX_VALUE/32: Math.abs(height[i] - height[i+1]);
		}
		score[0] += Math.abs(3 - height[0]) + Math.abs(1 - (height[2] - Math.max(height[0],height[1]))) + 0.01*number_of_holes[0] + 0.01*number_of_holes[1]+0.01*number_of_holes[0]+0.05;
		score[8] += Math.abs(1 - height[9]) + Math.abs(3 - (height[7] - Math.max(height[8], height[9]))) + 0.01*number_of_holes[9]+ 0.01*number_of_holes[8] +0.01*number_of_holes[9]+0.05;
		for(int i=1 ; i<8; i++){
			
			score[i] += Math.abs(3 - (height[i-1] - Math.max(height[i+1], height[i]))) + Math.abs(1 - (height[i+2] - Math.max(height[i+1], height[i]))) + 0.01*number_of_holes[i]+ 0.01*number_of_holes[i+1]+0.05;
		}
		for(int i=0; i<9; i++){//choose a lower height
			score[i] +=2*Math.max(height[i], height[i+1]);
			
			//positional divisibility penalty, if the position is divisible then good( low score) otherwise penalise according to remainder
			score[i] +=even_divisibility_weight*(flatness[i]%2);
			
			//general divisibilty penalty, this is a bias given by the width of the board
			score[i] += board_divisibility_weight*even_divisibility_weight*(number_of_cols %2);
		}
		
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//9-16
		score[9] = number_of_rows - Math.max(Math.max(height[0], height[1]), height[2]) <3? Double.MAX_VALUE/32 : Math.abs(1-(height[1] -height[0])) +Math.abs(1-(height[2]-height[0]))+Math.abs(height[1] - height[2]) + Math.abs(2-(height[3] - height[2])) +0.01*number_of_holes[0]+0.01*number_of_holes[1]+0.01*number_of_holes[2];
		score[16] = number_of_rows - Math.max(Math.max(height[7], height[8]), height[9]) <3? Double.MAX_VALUE/32: Math.abs(1 - (height[8] - height[7])) +Math.abs(1 -(height[9] - height[7])) +Math.abs(height[8] - height[9]) +Math.abs(2 - (height[6] - height[7])) +0.01*number_of_holes[7]+0.01*number_of_holes[8]+0.01*number_of_holes[9];
		
		for(int i=1; i<8; i++){
			
			score[i+9] = number_of_rows - Math.max(Math.max(height[i], height[i+1]), height[i+2]) <3? Double.MAX_VALUE/32 : Math.abs(1- (height[i+1] - height[i]))+Math.abs(1 -(height[i+2] - height[i])) +Math.abs(height[i+1]-height[i+2]) +Math.abs(2-(height[i-1] - height[i+1]))+0.01*number_of_holes[i]+0.01*number_of_holes[i+1]+0.01*number_of_holes[i+2];
		}
		
		for(int i=0; i<8; i++){
			score[i+9] += 2.*Math.max(Math.max(height[i], height[i+1]), height[i+2]);
			
			//positional divisibility penalty, if the position is divisible then good( low score) otherwise penalise according to remainder
			score[i+9] +=odd_divisibility_weight*(flatness[i]%3);
			
			//general divisibilty penalty, this is a bias given by the width of the board
			score[i+9] += board_divisibility_weight*odd_divisibility_weight*(number_of_cols %3);
		}
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//17-25
		score[17] = number_of_rows - Math.max(height[0], height[1])<4? Double.MAX_VALUE/32 : 1.5*Math.abs(2 - (height[0] - height[1])) +Math.abs(3 - (height[2] - height[1])) + 0.01*number_of_holes[0] +0.01 * number_of_holes[1];
		score[25] = number_of_rows - Math.max(height[8], height[9])<4? Double.MAX_VALUE/32: 1.5*Math.abs(2 - (height[8] - height[9])) + Math.abs(1 - (height[7] - height[8]))+ 0.01*number_of_holes[8] +0.01 * number_of_holes[9];
		
		for(int i=1; i<8; i++){
			score[i+17] = number_of_rows - Math.max(height[i], height[i+1]) <4? Double.MAX_VALUE/32 :1.5*Math.abs(2 - (height[i] - height[i+1])) +Math.abs(1 - (height[i-1] - height[i])) +Math.abs(3 - (height[i+2] - height[i+1]));
			
		}
		
		for(int i=0; i<9; i++){
			score[i+17] += 2*Math.max(height[i], height[i+1]);
			
			//positional divisibility penalty, if the position is divisible then good( low score) otherwise penalise according to remainder
			score[i+17] +=even_divisibility_weight*(flatness[i]%2);
			
			//general divisibilty penalty, this is a bias given by the width of the board
			score[i+17] += board_divisibility_weight*even_divisibility_weight*(number_of_cols %2);
		}
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//26-33
		//double big = Math.max(Math.max(height[0], height[1]), height[2]);
		//score[26] = number_of_rows - Math.max(Math.max(height[0], height[1]), height[2]) <3? Double.MAX_VALUE/32 : Math.abs(2 - (height[3] - height[2]));
		score[26] = number_of_rows - Math.max(Math.max(height[0], height[1]), height[2]) <3? Double.MAX_VALUE/32 : Math.abs(2 - (height[3] -height[2] )) +1.5*Math.abs(height[0] - height[1])+ 1.5*Math.abs(height[1] - height[2]) +1.5*Math.abs(height[0] - height[2])+ 0.01*number_of_holes[0]+ 0.01*number_of_holes[1]+ 0.01*number_of_holes[2];
		//score[33] = number_of_rows - Math.max(Math.max(height[0], height[1]), height[2]) <3? Double.MAX_VALUE/32 : Math.abs(1 - (height[6] - height[7]));
		//big = Math.max(Math.max(height[7], height[8]), height[9]);
		score[33] = number_of_rows - Math.max(Math.max(height[7], height[8]), height[9]) <3? Double.MAX_VALUE/32 : Math.abs(1 - (height[6] - height[7])) + 1.5*Math.abs(height[7] - height[8]) +1.5*Math.abs(height[8] - height[9]) +1.5*Math.abs(height[7] - height[9])+ 0.01*number_of_holes[7]+ 0.01*number_of_holes[8]+ 0.01*number_of_holes[9];
		
		for(int i=1; i<7; i++){
			//score[i+26] = number_of_rows - Math.max(Math.max(height[i], height[i+1]), height[i+2]) <3? Double.MAX_VALUE/32 : Math.abs(2 - (height[i+3] - height[i+2])) +Math.abs(1 - (height[i-1] - height[i]));
			//big = Math.max(Math.max(height[i], height[i+1]), height[i+2]);
			score[i+26] = number_of_rows - Math.max(Math.max(height[i], height[i+1]), height[i+2]) <3? Double.MAX_VALUE/32 : Math.abs(2 - (height[i+3] - height[i+2])) +Math.abs(1 - (height[i-1] - height[i])) +1.5*Math.abs(height[i] - height[i+1]) +1.5*Math.abs(height[i+1] - height[i+2]) +1.5*Math.abs(height[i] - height[i+2])+ 0.01*number_of_holes[i]+ 0.01*number_of_holes[i+1]+ 0.01*number_of_holes[i+2];
		}
		
		for(int i=0; i<8; i++){
			score[i+26] += 2*Math.max(Math.max(height[i], height[i+1]), height[i+2]) ;
			
			//positional divisibility penalty, if the position is divisible then good( low score) otherwise penalise according to remainder
			score[i+26] +=odd_divisibility_weight*(flatness[i]%3);
			
			//general divisibilty penalty, this is a bias given by the width of the board
			score[i+26] += board_divisibility_weight*odd_divisibility_weight*(number_of_cols %3);
		}
	}
	//DONE
	private void initialize0(){
		score = new double[9];
		
		for(int i=0; i<number_of_cols-1; i++){
			
			score[i] =number_of_rows -Math.max(height[i], height[i+1])<4? Double.MAX_VALUE/32: Math.abs(height[i] - height[i+1]) + 0.01 * number_of_holes[i];
		}
		
		//score[0] += Math.abs(2 - Math.abs(number_of_rows-height[0])) + Math.abs(2 - Math.abs(height[2] - height[1])) + 0.01*number_of_holes[0] + 0.01*number_of_holes[1];
		score[0] += Math.abs(2 - (height[2] - Math.max(height[0],height[1]))) + 0.01*number_of_holes[0] + 0.01*number_of_holes[1];
		//score[8] += Math.abs(2 - Math.abs(number_of_rows-height[9])) + Math.abs(2 - Math.abs(height[7] - height[8])) + 0.01*number_of_holes[9]+ 0.01*number_of_holes[8];
		score[8] += Math.abs(2 - (height[7] - Math.max(height[8], height[9]))) + 0.01*number_of_holes[9]+ 0.01*number_of_holes[8];
		for(int i=1 ; i<8; i++){
			
			score[i] += Math.abs(2 - (height[i-1] -Math.max(height[i+1], height[i]))) + Math.abs(2 - (height[i+2] - Math.max(height[i+1], height[i]))) + 0.01*number_of_holes[i]+ 0.01*number_of_holes[i+1];
		}
		for(int i=0; i<number_of_cols-1; i++){//choose a lower height
			score[i] +=2*Math.max(height[i], height[i+1]);
		}
		
		diagnostic();
	}
	//DONE
	private void initialize1() {
		
		score = new double[17];
		
		score[0] =number_of_rows - height[0] < 5? Double.MAX_VALUE/32:Math.abs(4 - (height[1] - height[0])) + 0.01*number_of_holes[0];
		score[9] = number_of_rows - height[9] < 5? Double.MAX_VALUE/32:Math.abs(4 - (height[8] - height[9])) + 0.01*number_of_holes[9];
		for(int i=1 ; i<9; i++){
			
			score[i] = number_of_rows - height[i] < 5? Double.MAX_VALUE/32: Math.abs(4 - (height[i-1] - height[i]) ) + Math.abs(4 - (height[i+1] - height[i]) ) + 0.01*number_of_holes[i];
		}
		
		for(int i=0; i<number_of_cols; i++){//choose a lower height
			score[i] +=2*height[i];
		}
		//10-16
		double big = Math.max(Math.max(Math.max(height[0], height[1]), height[2]), height[3]);
		
		score[10] =number_of_rows - big < 2? Double.MAX_VALUE/32:Math.abs(1 - (height[4] - big)) +Math.abs(height[0] - height[1]) +Math.abs(height[1] - height[2]) +Math.abs(height[2] - height[3]) + Math.abs(height[0] - height[2]) +Math.abs(height[1] - height[3]) +Math.abs(height[0] - height[3]) +0.01*number_of_holes[0]+0.01*number_of_holes[1]+0.01*number_of_holes[2]+0.01*number_of_holes[3];
		big = Math.max(Math.max(Math.max(height[9], height[8]), height[7]), height[6]);
		score[16] = number_of_rows - big < 2? Double.MAX_VALUE/32:Math.abs(1 - (height[5] - big)) +Math.abs(height[9] - height[8]) +Math.abs(height[8] - height[7]) +Math.abs(height[7] - height[6]) + Math.abs(height[6] - height[8]) +Math.abs(height[7] - height[9]) +Math.abs(height[6] - height[9]) +0.01*number_of_holes[9]+0.01*number_of_holes[8]+0.01*number_of_holes[7]+0.01*number_of_holes[6];
		
		for(int i=1; i<6;i++){
			double biggest = Math.max(Math.max(Math.max(height[i], height[i+1]), height[i+2]), height[i+3]);
			
			score[i+10] = number_of_rows - height[i] < 2? Double.MAX_VALUE/32: Math.abs(1 - (height[i-1] - biggest) ) + Math.abs(1 - (biggest - height[i+4])) + Math.abs(height[i] - height[i+1])+ Math.abs(height[i+1] - height[i+2]) + Math.abs(height[i+2] - height[i+3])+Math.abs(height[i] - height[i+2]) +Math.abs(height[i+1] - height[i+3]) +Math.abs(height[i] - height[i+3]) +0.01*number_of_holes[i]+0.01*number_of_holes[i+1]+0.01*number_of_holes[i+2]+0.01*number_of_holes[i+3];
		}
		
		for(int i=0; i<7; i++){
			score[i+10] +=(height[i]+height[i+1]+height[i+2]+height[i+3])/2;
		}
		diagnostic();
	}

	
	int last_piece = -1;
	
	int[] height;
	int[] number_of_holes;
	int[] flatness; //largest stretch of flatness to the right of i
	final int number_of_rows = 21;
	final int number_of_cols = 10;
	public void featureSelector(State s){
		last_piece = s.getNextPiece();
		int[][] field = s.getField();
		
		boolean[] found_height = new boolean[number_of_cols];
		height = new int[number_of_cols];
		number_of_holes = new int[number_of_cols];//these are buried holes
		flatness = new int[number_of_cols];
		for(int col=0; col<number_of_cols; col++){
			
			//find largest stretch of flatness to the right of col
			for(int right = col+1; right<number_of_cols; right++){
				
				if(height[col] == height[right]){
					flatness[col ]++;
				}else{
					break;
				}
			}
			
			for(int row = number_of_rows-1; row>=0; row--){
				
				//find height of this col
				if(!found_height[col] && field[row][col]>0){
					
					found_height[col] = true;
					
					height[col] = row+1;
				}
				
				//count number of holes
				if(found_height[col] && field[row][col]<=0){
					
					number_of_holes[col]++;
				}
			}
		}
		

	}

	public static void main(String[] args) {
		State s = new State();
		new TFrame(s);
		PlayerSkeleton p = new PlayerSkeleton();
		while(!s.hasLost()) {
			s.makeMove(p.pickMove(s,s.legalMoves()));
			s.draw();
			s.drawNext(0,0);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("You have completed "+s.getRowsCleared()+" rows.");
	}
	
	
	public void diagnostic(){
		for(int i=0; i<score.length; i++){
			System.out.print(i+": "+score[i]+", ");
		}
		System.out.println("");
		
	}
}
