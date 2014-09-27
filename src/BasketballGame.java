import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BasketballGame {

	public static void main(String[] args) throws FileNotFoundException {

		PrintWriter writer = new PrintWriter("basketball_game_output.txt");
		Scanner in = new Scanner(new FileReader(
				"basketball_game.txt"));
		int cases = in.nextInt();
		int caseCount = 1;

		while (cases > 0) {

			int totalPlayers = in.nextInt();
			int gameTime = in.nextInt();
			int playersAtOneTime = in.nextInt();
			ArrayList<Player> list = new ArrayList<Player>();
			while (totalPlayers > 0) {

				String name = in.next();
				int shot_percentage = in.nextInt();
				int height = in.nextInt();
				list.add(new Player(name, shot_percentage, height));
				totalPlayers--;
				
			}
	
			// Players correctly sorted
			Collections.sort(list);

			for (int i = 0; i < list.size(); i++) {
				list.get(i).setDraftNumber(i + 1);
			}

			ArrayList<Player> team1 = new ArrayList<Player>();
			ArrayList<Player> team2 = new ArrayList<Player>();

			for (int i = 0; i < list.size(); i += 2) {
				team1.add(list.get(i));
			}

			for (int i = 1; i < list.size(); i += 2) {
				team2.add(list.get(i));
			}
			
			String team1Names = playersRemaining(team1, gameTime,
					playersAtOneTime);
			String team2Names = playersRemaining(team2, gameTime,
					playersAtOneTime);

			writer.println("Case #" + caseCount +": " + team1Names + team2Names);
			caseCount++;
			cases--;
		}
		writer.close();

	}

	private static String playersRemaining(ArrayList<Player> team1,
			int gameTime, int playersAtOnce) {

		Player[] onPitch1 = new Player[playersAtOnce];

		for (int i = 0; i < playersAtOnce; i++) {
			onPitch1[i] = team1.remove(0);
		}

		while (gameTime > 0) {

			for (int i = 0; i < playersAtOnce; i++) {
				onPitch1[i].increaseTime();

			}

			int maxTimeOnPitch1 = 0;
			ArrayList<Integer> candidatesToSub1 = new ArrayList<Integer>();

			for (int i = 0; i < playersAtOnce; i++) {
				if (onPitch1[i].getPlayingTime() >= maxTimeOnPitch1) {
					maxTimeOnPitch1 = onPitch1[i].getPlayingTime();
				}

			}

			for (int i = 0; i < playersAtOnce; i++) {
				if (onPitch1[i].getPlayingTime() == maxTimeOnPitch1) {
					candidatesToSub1.add(i);
				}

			}

			int maxDraft = 0;
			for (Integer index : candidatesToSub1) {
				if (onPitch1[index].getDraftNumber() >= maxDraft) {
					maxDraft = onPitch1[index].getDraftNumber();
				}
			}

			for (int i = 0; i < onPitch1.length; i++) {
				if (onPitch1[i].getDraftNumber() == maxDraft) {
					team1.add(onPitch1[i]);
					onPitch1[i] = team1.remove(0);
				}
			}
			gameTime--;
		}

		String namesOfPlayers = "";
		for (int i = 0; i < onPitch1.length; i++) {
			namesOfPlayers += onPitch1[i].getName() + " ";
		}
		return namesOfPlayers;
	}

	private static class Player implements Comparable<Player> {

		private String name;
		private int shotPercentage;
		private int height;
		private int playingTime;
		private int draftNumber;

		public Player(String name, int shotPercentage, int height) {
			this.name = name;
			this.shotPercentage = shotPercentage;
			this.height = height;
			this.playingTime = 0;
			this.draftNumber = 0;
		}

		public int getDraftNumber() {
			return draftNumber;
		}

		public String getName() {
			return name;
		}

		public int getPlayingTime() {
			return playingTime;
		}

		public void increaseTime() {
			playingTime++;
		}

		public void setDraftNumber(int draftNumber) {
			this.draftNumber = draftNumber;
		}

		@Override
		public int compareTo(Player o) {
			if (this.shotPercentage < o.shotPercentage) {
				return 1;
			} else if (this.shotPercentage > o.shotPercentage) {
				return -1;
			} else if (this.shotPercentage == o.shotPercentage) {
				if (this.height < o.height) {
					return 1;
				} else if (this.height > o.height) {
					return -1;
				} else {
					return 0;
				}
			}
			return 0;
		}

	}

}
