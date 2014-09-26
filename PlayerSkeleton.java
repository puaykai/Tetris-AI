import java.io.*;
import java.util.*;


public class PlayerSkeleton {
	
	int[][] all_moves = {{0,1,2,3,4,5,6,7,8,9},
										 {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16},
										 {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33},
										 {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33},
										 {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33},
										 {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16},
										 {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16}};
	
	static String experience_file_path = "D:\\tetris_experience.txt";
	ArrayList<String> experience_array = new ArrayList<String>();
	HashMap<String, ArrayList<MoveEvaluation>> map = new HashMap<String, ArrayList<MoveEvaluation>>();
	
	Integer last_move = null;
	
	int last_rows_cleared = 0;
	
	int[] last_features = null;
	
	int last_piece = -1;
	
	final int number_of_rows = 21;
	final int number_of_cols = 10;
	
	public PlayerSkeleton(){
		
		//System.out.println("recalling experiences");
		experienceRecall();
		System.out.println("experience recalled! Dictionary_size:"+map.size());
	}
	
	//Move Evaluation Pair
	class MoveEvaluation{
		
		Integer move = null;
		Double score = null;
		
		public MoveEvaluation(Integer move, Double score){
			
			this.move = move;
			this.score = score;
		}
	}
	
	//Takes in a evaluation and press it to a number
	public double heuristicFunction(String[] eval){
		
		double steps_taken = Double.parseDouble(eval[1]);
		double steps_to_finish = Double.parseDouble(eval[2*number_of_cols+2]);
		
		//check if it is a death move
		if(steps_taken == steps_to_finish) return Double.MIN_VALUE;
		
		double rows_clear = Double.parseDouble(eval[0]);

		double[] increase_in_height = new double[number_of_cols];
		double[] increase_in_holes = new double[number_of_cols];
		for(int i=0; i<number_of_cols;i++){
			
			increase_in_height[i] = Double.parseDouble(eval[i]+2);
		}
		for(int i=0; i<number_of_cols; i++){
			
			increase_in_holes[i] = Double.parseDouble(eval[i]+12);
		}
		//////////////////WEIGHTS
		double row_clear_weights = 50;
		double height_weights = -1;
		double hole_weights = -1;
		double steps_weights = 1;
		/////////////////
		
		double score = 0;
		
		for(int i = 0; i<number_of_cols; i++){
			
			score += height_weights * increase_in_height[i];
			score += hole_weights * increase_in_holes[i];
		}
		
		score += row_clear_weights * rows_clear + steps_weights *steps_taken;
		return score;
	}

	/**
	 * EXPERIENCE VECTOR : 
	 * STATE(piece, 10 x height,10 x holes), 
	 * MOVE, 
	 * EVALUATION(rows_cleared, steps taken,10 x increase in height,10 x increase in holes, total moves in game)
	 * */
	public void experienceRecall(){
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(experience_file_path));
			String line = "";
			while((line = reader.readLine())!=null){
				
				String[] array = line.split(":");//0 - STATE, 1 - MOVE, 2 - EVALUATION
				
				//String[] feature_split = array[0].split("!");
				
				Integer move = Integer.parseInt(array[1]);
				
				String[] evaluation_array = array[2].split(",");
				
				//ArrayList<MoveEvaluation> eval_list = map.get(feature_split[0]);
				ArrayList<MoveEvaluation> eval_list = map.get(array[0]);
				
				if(eval_list ==null){
					
					ArrayList<MoveEvaluation> new_list = new ArrayList<MoveEvaluation>();
					
					new_list.add( new MoveEvaluation(move,heuristicFunction(evaluation_array)));
					
					//map.put(feature_split[0],new_list);
					map.put(array[0],new_list);
				}else{
					
					MoveEvaluation me =  new MoveEvaluation(move,heuristicFunction(evaluation_array));
					
					if(!eval_list.contains(me)) eval_list.add(me);
				}
				
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void featureSelector(State s){
		
		last_piece = s.getNextPiece();
		int[][] field = s.getField();
		
		boolean[] found_height = new boolean[number_of_cols];
		int[] height = new int[number_of_cols];
		int[] number_of_holes = new int[number_of_cols];//these are buried holes
		for(int col=0; col<number_of_cols; col++){
			for(int row = number_of_rows-1; row>=0; row--){
				
				//find height of this col
				if(!found_height[col] && field[row][col]>0){
					
					found_height[col] = true;
					
					height[col] = row;
				}
				
				//count number of holes
				if(found_height[col] && field[row][col]<=0){
					
					number_of_holes[col]++;
				}
			}
		}
		
		//save as last_features
		last_features = new int[2*number_of_cols];
		for(int i=0; i<number_of_cols; i++){
			
			last_features[i] = height[i];
			
			last_features[number_of_cols+i] = number_of_holes[i] ;
		}
		
	}
	
	public void moveEvaluator(State current_state){
		
		if(last_features == null){
			
			featureSelector(current_state);
			
			return;
		}
		
		int number_of_rows_cleared = current_state.getRowsCleared() - last_rows_cleared;
		
		last_rows_cleared = current_state.getRowsCleared();//update last_rows_cleared
		
		int number_of_steps = current_state.getTurnNumber();
		
		int l_piece = this.last_piece;
		
		int[] last_features = this.last_features;
		
		featureSelector(current_state);
		
		int[] evaluation_vector = new int[last_features.length];
		for(int i=0; i<2*number_of_cols;i++){
			
			evaluation_vector[i] = this.last_features[i] - last_features[i];//INCREASEin height/number_of_holes
		}
		
		//converts to EXPERIENCE STRING
		String experience_string = l_piece+"";
		for(int i=0; i<last_features.length; i++){
			
			
			if(i==(number_of_cols)){
				
				experience_string += "!"+last_features[i];
			}else{
				
				experience_string += ","+last_features[i];
			}
		}
		
		experience_string += ":"+last_move+":"+number_of_rows_cleared+","+number_of_steps+",";
		
		for(int i=0; i<evaluation_vector.length; i++){
			
			experience_string += evaluation_vector[i]+",";
		}
		
		experience_array.add(experience_string);
		
		if(current_state.hasLost()){
			
			ArrayList<String> temp = new ArrayList<String>();
			
			for(String exp : experience_array){
				
				temp.add(exp+current_state.getTurnNumber());
			}
			
			experience_array = temp;
			
		}
		
		//System.out.println("EXPERIENCE VECTOR: "+experience_string);//prints the EXPERIENCE VECTOR : STATE(piece, height, holes), MOVE, EVALUATION(rows_cleared, steps taken, increase in height, increase in holes, total moves in game)
	}

	//implement this function to have a working system
	public int pickMove(State s, int[][] legalMoves) {
		
		int[] all_legal_moves = all_moves[s.nextPiece];
		
		moveEvaluator(s);
		
		//featureSelector(s);
		Random random = new Random();

		//select a random move
		int nextPiece = s.getNextPiece();
		int rand_start_piece = random.nextInt(legalMoves.length);
		//int number_of_moves = legalMoves[nextPiece].length;
		int number_of_moves = legalMoves[rand_start_piece].length;
		int random_index = random.nextInt(number_of_moves);
		int selected_move = legalMoves[rand_start_piece][random_index];//random initialization
		
		//make the featureString
		String featureString = nextPiece+"";
		for(int i = 0; i<number_of_cols; i++){
			
			featureString += ","+last_features[i];
		}
		
		//System.out.println("Feature key: "+featureString);
		
		ArrayList<MoveEvaluation> possible_moves = map.get(featureString);
		
		if(possible_moves != null && !possible_moves.isEmpty()){
			
			System.out.println("Saw situation before!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			
			Double best = -20000.0;
			
			for(MoveEvaluation move : possible_moves){//choose best score
				
				if(move.score>best){
					selected_move = move.move;
					best = move.score;
				}
			}
			System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBest score: "+best+" move: "+selected_move);
			//here try a new move that we never try before
			if(random.nextDouble()>Math.exp(best)){
				
				
				
				for(Integer legal_move : all_legal_moves){
					boolean found = false;
					for(MoveEvaluation possible_move : possible_moves){
						
						//System.out.println("Comparing: "+possible_move.move+" , "+legal_move);
						
						if(legal_move.equals(possible_move.move)){
							
							//System.out.println("Used");
							
							found = true;
							break;
						}
					}
					if(!found){
						
						selected_move = legal_move;
						System.out.println("REPLACEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD "+selected_move);
						break;
					}
				}
				
			}
		}
		
		//System.out.println("Move selected: "+selected_move);
		
		//record last move and last state
		last_move = selected_move;
		
		return selected_move;
	}
	
	public static void main(String[] args) {
		//////////////////////////////////For training only
		int number_of_games  =10000;

		//////////////////////////////////
		for(int game_index = 0; game_index<number_of_games; game_index++){//FOR TRAINING ONLY
			System.out.println("Playing game: "+game_index);
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
			
			///////////////////////////////For training only, to be removed
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(new File(experience_file_path),true));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			p.moveEvaluator(s);
			for(String experience : p.experience_array){
				
				try {
					writer.append(experience+"\n");
					writer.flush();
					//writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			///////////////////////////////
			
			System.out.println("You have completed "+s.getRowsCleared()+" rows.");
		}

		
	}
	
}
