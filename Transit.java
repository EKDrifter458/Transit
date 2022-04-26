package transit;

import java.util.ArrayList;

public class Transit {
	private TNode trainZero;

	
	public Transit() { trainZero = null; }

	public Transit(TNode tz) { trainZero = tz; }
	

	public TNode getTrainZero () {
		return trainZero;
	}


	public void makeList(int[] trainStations, int[] busStops, int[] locations) {	    
		TNode walk, bus, train;
		TNode walkLoci, busLoci, trainLoci;

		walk = new TNode();
		bus = new TNode(0, null, walk);
		train = new TNode(0, null, bus);

		walkLoci = walk;
		busLoci = bus;
		trainLoci = train;
		trainZero = train;

		int w = 0, b = 0, t = 0;
		while(w<locations.length){
			walkLoci.setNext(new TNode());
			walkLoci = walkLoci.getNext();
			walkLoci.setLocation(locations[w]);
			

			if(b<busStops.length && busStops[b] == locations[w]){
				busLoci.setNext(new TNode());
				busLoci = busLoci.getNext();
				busLoci.setLocation(busStops[b]);
				busLoci.setDown(walkLoci);
				

				if(t<trainStations.length && trainStations[t] == busStops[b]){
					trainLoci.setNext(new TNode());
					trainLoci = trainLoci.getNext();
					trainLoci.setLocation(trainStations[t]);
					trainLoci.setDown(busLoci);
					
					t++;
				}
				b++;
			}
			w++;
		}
		
	}
	

	public void removeTrainStation(int station) {

		if (trainZero.getLocation() == station) {
			trainZero = trainZero.getNext();
			return;
		}
		TNode previous = trainZero;
		TNode temp = trainZero.getNext();

        while (temp != null) {
            if (temp.getLocation() == station) {
                    previous.setNext(temp.getNext());
            }
			
			previous = temp;
			temp = temp.getNext();
        }
    }

	public void addBusStop(int busStop) {
	    TNode temp = trainZero.getDown().getNext();
		TNode prev = trainZero.getDown();
		
		if (prev.getLocation() == busStop)
			return;

		while (temp!=null && temp.getLocation()<busStop){
			prev = temp;
			temp = temp.getNext();
		}
		prev.setNext(new TNode(busStop, prev.getNext(), new TNode()));
		temp = prev.getNext();

		TNode temp2 = trainZero.getDown().getDown();
		while (temp2!=null) {
			if (temp2.getLocation()==temp.getLocation())
				temp.setDown(temp2);
			temp2 = temp2.getNext();
		}
			

	}
	

	public ArrayList<TNode> bestPath(int destination) {
		ArrayList<TNode> journey = new ArrayList<>();

		TNode temp0 = trainZero.getNext();
		TNode prev0 = trainZero;
		
		while (temp0!= null && temp0.getLocation() <= destination) {
			journey.add(prev0);
			if (prev0.getLocation()==destination) {
				return journey;
			}
			prev0 = temp0;
			temp0 = temp0.getNext();
		}

		journey.add(prev0);

		TNode temp1 = prev0.getDown().getNext();
		TNode prev1 = prev0.getDown();
		
		while (temp1!= null && temp1.getLocation() <= destination) {
			journey.add(prev1);
			if (prev1.getLocation()==destination) {				
				return journey;
			}
			prev1 = temp1;
			temp1 = temp1.getNext();
		}

		journey.add(prev1);
		
		TNode temp2 = prev1.getDown().getNext();
		TNode prev2 = prev1.getDown();
		
		while (temp2!= null && temp2.getLocation() <= destination) {
			journey.add(prev2);
			if (prev2.getLocation()==destination) {
				return journey;
			}
			prev2 = temp2;
			temp2 = temp2.getNext();
		}

		journey.add(prev2);

		return journey;
		
	}


	public TNode duplicate() {
		ArrayList<Integer> trainStations = new ArrayList<Integer>();
		TNode temp = trainZero.getNext();
		while (temp!=null) {
			trainStations.add(temp.getLocation());
			temp = temp.getNext();
		}

		ArrayList<Integer> busStops = new ArrayList<Integer>();
		TNode temp1 = trainZero.getDown().getNext();
		while (temp1!=null) {
			busStops.add(temp1.getLocation());
			temp1 = temp1.getNext();
		}

		ArrayList<Integer> locations = new ArrayList<Integer>();
		TNode temp2 = trainZero.getDown().getDown().getNext();
		while (temp2!=null) {
			locations.add(temp2.getLocation());
			temp2 = temp2.getNext();
		}

		

		TNode walk, bus, train;
		TNode walkLoci, busLoci, trainLoci;

		walk = new TNode();
		bus = new TNode(0, null, walk);
		train = new TNode(0, null, bus);

		walkLoci = walk;
		busLoci = bus;
		trainLoci = train;
		TNode dupes = train;

		int w = 0, b = 0, t = 0;
		while(w<locations.size()){
			walkLoci.setNext(new TNode());
			walkLoci = walkLoci.getNext();
			walkLoci.setLocation(locations.get(w));
			

			if(b<busStops.size() && busStops.get(b) == locations.get(w)){
				busLoci.setNext(new TNode());
				busLoci = busLoci.getNext();
				busLoci.setLocation(busStops.get(b));
				busLoci.setDown(walkLoci);
				

				if(t<trainStations.size() && trainStations.get(t) == busStops.get(b)){
					trainLoci.setNext(new TNode());
					trainLoci = trainLoci.getNext();
					trainLoci.setLocation(trainStations.get(t));
					trainLoci.setDown(busLoci);
					
					t++;
				}
				b++;
			}
			w++;
		}
		return dupes;

		
	}


	public void addScooter(int[] scooterStops) {
		TNode bus = trainZero.getDown();
		TNode walk = trainZero.getDown().getDown();
        TNode scooter = new TNode(0, null, walk);
        bus.setDown(scooter);
        bus = bus.getNext();

        int counter = 0;
        while (counter < scooterStops.length && walk.getNext() != null) {
            walk = walk.getNext();
            if (walk.getLocation() == scooterStops[counter]) {
                TNode a = new TNode(walk.getLocation(), null, walk);
				scooter.setNext(a);
                scooter = scooter.getNext();
                if (bus != null && bus.getLocation() == scooterStops[counter]) {
                    bus.setDown(scooter);
                    bus = bus.getNext();
                }
                counter++;
            }
        }

        while (counter < scooterStops.length) {
            TNode b = new TNode(scooterStops[counter], null, null);
			scooter.setNext(b);
            scooter = scooter.getNext();
            counter++;
			
        }
	}


	public void printList() {
		
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

		
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
