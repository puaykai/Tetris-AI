package Human;

import Human.State;

import java.util.*;


public class PlayerSkeleton {
	
	int last_piece = -1;
	
	int[] height;
	int[] number_of_holes;
	final int number_of_rows = 21;
	final int number_of_cols = 10;
	
	public void featureSelector(State s){
		last_piece = s.getNextPiece();
		int[][] field = s.getField();
		
		boolean[] found_height = new boolean[number_of_cols];
		height = new int[number_of_cols];
		number_of_holes = new int[number_of_cols];//these are buried holes
		for(int col=0; col<number_of_cols; col++){
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

	//implement this function to have a working system
	double[] score = new double[11];
	public int pickMove(State s, int[][] legalMoves) {
		
		for(int i=0; i<legalMoves.length; i++){
			for(int j=0; j<legalMoves[i].length; j++){
				System.out.print(legalMoves[i][j]+",");
			}
			System.out.println("");
		}
		
		int selected_move = 0;

		score[9] = Double.MAX_VALUE/32;
		//////////////////Survey the land...
		featureSelector(s);
		for(int i=0; i<number_of_cols; i++){
			
			System.out.print(height[i]+",");
		}
		System.out.println("");
		////////////////////////////////
		
		//////////////////Rank the spots

		switch(s.getNextPiece()){
		
		case 1:
			score[0] =number_of_rows - height[0] < 5? Double.MAX_VALUE/32:height[0] + Math.abs(4 - (height[1] - height[0])) + 0.01*number_of_holes[0];
			score[9] = number_of_rows - height[9] < 5? Double.MAX_VALUE/32:height[9] + Math.abs(4 - (height[8] - height[9])) + 0.01*number_of_holes[9];
			for(int i=1 ; i<9; i++){
				
				score[i] = number_of_rows - height[i] < 5? Double.MAX_VALUE/32: Math.abs(4 - (height[i-1] - height[i]) ) + Math.abs(4 - (height[i+1] - height[i]) ) + 0.01*number_of_holes[i];
			}
			
			for(int i=0; i<number_of_cols; i++){//choose a lower height
				score[i] +=2*height[i];
			}
			diagnostic();
			break;
		case 0:
		case 2:
		case 3:
			for(int i=0; i<number_of_cols-1; i++){
				
				score[i] =number_of_rows -Math.max(height[i], height[i+1])<4? Double.MAX_VALUE/32: Math.abs(height[i] - height[i+1]) + 0.01 * number_of_holes[i];
			}
			diagnostic();
			switch(s.getNextPiece()){
			case 2:
				score[0] += Math.abs(3 - height[0]) + Math.abs(1 - (height[2] - Math.max(height[0],height[1]))) + 0.01*number_of_holes[0] + 0.01*number_of_holes[1];
				score[8] += Math.abs(1 - height[9]) + Math.abs(3 - (height[7] - Math.max(height[8], height[9]))) + 0.01*number_of_holes[9]+ 0.01*number_of_holes[8];
				for(int i=1 ; i<8; i++){
					
					score[i] += Math.abs(3 - (height[i-1] - Math.max(height[i+1], height[i]))) + Math.abs(1 - (height[i+2] - Math.max(height[i+1], height[i]))) + 0.01*number_of_holes[i]+ 0.01*number_of_holes[i+1];
				}
				break;
			case 3:
				//score[0] += Math.abs(1 - height[0]) + Math.abs(3 - (height[2] - Math.max(height[0],height[1]))) + 0.01*number_of_holes[0] + 0.01*number_of_holes[1];
				score[0] += Math.abs(3 - (height[2] - Math.max(height[0],height[1]))) + 0.01*number_of_holes[0] + 0.01*number_of_holes[1];
				//score[8] += Math.abs(3 - height[9]) + Math.abs(1 - (height[7] - Math.max(height[8], height[9]))) + 0.01*number_of_holes[9]+ 0.01*number_of_holes[8];
				score[8] += Math.abs(1 - (height[7] - Math.max(height[8], height[9]))) + 0.01*number_of_holes[9]+ 0.01*number_of_holes[8];
				for(int i=1 ; i<8; i++){
					
					score[i] += Math.abs(1 - (height[i-1] - Math.max(height[i+1], height[i]))) + Math.abs(3 - (height[i+2] - Math.max(height[i+1], height[i]))) + 0.01*number_of_holes[i]+ 0.01*number_of_holes[i+1];
				}
				break;
			case 0:
				//score[0] += Math.abs(2 - Math.abs(number_of_rows-height[0])) + Math.abs(2 - Math.abs(height[2] - height[1])) + 0.01*number_of_holes[0] + 0.01*number_of_holes[1];
				score[0] += height[0] + Math.abs(2 - (height[2] - Math.max(height[0],height[1]))) + 0.01*number_of_holes[0] + 0.01*number_of_holes[1];
				//score[8] += Math.abs(2 - Math.abs(number_of_rows-height[9])) + Math.abs(2 - Math.abs(height[7] - height[8])) + 0.01*number_of_holes[9]+ 0.01*number_of_holes[8];
				score[8] += height[8] + Math.abs(2 - (height[7] - Math.max(height[8], height[9]))) + 0.01*number_of_holes[9]+ 0.01*number_of_holes[8];
				for(int i=1 ; i<8; i++){
					
					score[i] += Math.abs(2 - (height[i-1] -Math.max(height[i+1], height[i]))) + Math.abs(2 - (height[i+2] - Math.max(height[i+1], height[i]))) + 0.01*number_of_holes[i]+ 0.01*number_of_holes[i+1];
				}
				
				break;
			}
			for(int i=0; i<number_of_cols-1; i++){//choose a lower height
				score[i] +=2*Math.max(height[i], height[i+1]);
			}
			diagnostic();
			break;
		case 5:
			for(int i=0; i<number_of_cols-2; i++){
				
				score[i] =number_of_rows -height[i]<3? Double.MAX_VALUE/32: Math.abs(height[i] - height[i+1]) + 0.01 * number_of_holes[i];
			}
			score[0] += Math.abs(1 - Math.abs(number_of_rows-height[0])) + Math.abs(1 - Math.abs(height[2] - height[1])) + 0.01*number_of_holes[0] + 0.01*number_of_holes[1] + 0.01*number_of_holes[2];
			score[7] += Math.abs(1 - Math.abs(number_of_rows-height[9])) + Math.abs(1 - Math.abs(height[7] - height[6])) + 0.01*number_of_holes[9] + 0.01*number_of_holes[8]+ 0.01*number_of_holes[7];
			for(int i=1 ; i<7; i++){
				
				score[i] += Math.abs(1 - Math.abs(height[i] - height[i-1])) + Math.abs(1 - Math.abs(height[i+2] - height[i+1])) + Math.abs(2 - Math.abs(height[i+2]-height[i+3])) + 0.01*number_of_holes[i]+ 0.01*number_of_holes[i+1] - 0.001*number_of_holes[i+2];
			}
			break;

		case 6:
			score[0] = number_of_rows -height[0]<3? Double.MAX_VALUE/32: Math.abs(2 - Math.abs(number_of_rows-height[0])) +Math.abs(1 - Math.abs(height[1]-height[0])) + Math.abs(height[2]-height[1]) + Math.abs(1 - Math.abs(height[3]-height[2])) + 0.01*number_of_holes[0] + 0.01*number_of_holes[1] - 0.001*number_of_holes[2];
			score[7] = number_of_rows -height[8]<3? Double.MAX_VALUE/32: Math.abs(1 - Math.abs(number_of_rows-height[9])) +Math.abs(1 - Math.abs(height[7]-height[8])) + Math.abs(height[8]-height[9]) + Math.abs(1 - Math.abs(height[6]-height[7])) + 0.01*number_of_holes[7] + 0.01*number_of_holes[8] - 0.001*number_of_holes[9];
			
			for(int i=1; i<7; i++){
				
				score[i] =number_of_rows -height[i]<3? Double.MAX_VALUE/32: Math.abs(2 - Math.abs(height[i-1]-height[i])) +Math.abs(1 - Math.abs(height[i]-height[i+1])) + Math.abs(height[i+1]-height[i+2]) + Math.abs(1 - Math.abs(height[i+2]-height[i+3])) + 0.01*number_of_holes[i] + 0.01*number_of_holes[i+1] - 0.001*number_of_holes[i+2];
			}
			break;
		case 4:
			score[0] =number_of_rows - height[0] < 4? Double.MAX_VALUE/32:Math.abs(3 - Math.abs(number_of_rows-height[0])) +(height[1] - height[0]-1)+Math.abs(2 - Math.abs(height[1]-height[2])) + 0.01*number_of_holes[0]+ 0.01*number_of_holes[1];
			score[8] =number_of_rows - height[8] < 4? Double.MAX_VALUE/32:Math.abs(2 - Math.abs(number_of_rows-height[9])) +(height[8] - height[9]-1)+Math.abs(3 - Math.abs(height[7]-height[8])) + 0.01*number_of_holes[8]+ 0.01*number_of_holes[9];
			
			
			for(int i=1; i<7;i++){
				
				score[i] = number_of_rows - height[i] < 4? Double.MAX_VALUE/32:Math.abs(3 - Math.abs(height[i-1]-height[i])) +(height[i+1] - height[i]-1)+ Math.abs(2 - Math.abs(height[i+1]-height[i+2]))+ 0.01*number_of_holes[i]+ 0.01*number_of_holes[i+1];
			}
			break;
		}
		

		diagnostic();
		////////////////////////////
		
		////////////////////////////Choose the piece
		double best = Double.MAX_VALUE/32;
		for(int i=0; i<number_of_cols; i++){
			if(best>score[i]){
				best = score[i];
				selected_move = i;
			}
		}
		
		////////////////////////////
		System.out.println("");
		System.out.println("Move: "+selected_move+" Piece: "+s.getNextPiece());
		//return selected_move;
		return 10;
	}
	
	public void diagnostic(){
		for(int i=0; i<number_of_cols; i++){
			System.out.print(score[i]+",");
		}
		System.out.println("");
		
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
	
}
