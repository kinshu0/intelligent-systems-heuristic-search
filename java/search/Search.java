package search;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

import subway.SubwayMap;

/**
This code is adapted from search.py in the AIMA Python implementation, which is published with the license below:

	The MIT License (MIT)

	Copyright (c) 2016 aima-python contributors

	Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


**/

public class Search{
	/* DO NOT MODIFY THE HEADERS OF ANY OF THESE FUNCTIONS */
	
	// Uninformed Search algorithms
	
	public static Node breadthFirstSearch(Problem problem){
		HashSet<String> visited = new HashSet<>();
		LinkedList<Node> frontier = new LinkedList<>();

		Node initialNode = new Node(problem.getInitial(), null, null, 0);

		frontier.add(initialNode);

		while (!frontier.isEmpty()) {
			Node top = frontier.poll();
			visited.add(top.toString());

			ArrayList<Node> adjNodes = top.expand(problem);

			if (adjNodes.isEmpty()) {
				continue;
			}

			for (Node v: adjNodes) {
				if (!visited.contains(v.toString())) {
					frontier.add(v);
					if (problem.goalTest(v.getState())) {
						printSolution(v.path(), (int) v.getPathCost(), visited.size());
						return v;
					}
				}
			}
		}

		return null;
	}
	
	public static Node depthFirstSearch(Problem problem){
		HashSet<String> visited = new HashSet<>();
		LinkedList<Node> frontier = new LinkedList<>();

		Node initialNode = new Node(problem.getInitial(), null, null, 0);

		frontier.add(initialNode);

		while (!frontier.isEmpty()) {
			Node top = frontier.pop();
			visited.add(top.toString());

			ArrayList<Node> adjNodes = top.expand(problem);

			if (adjNodes.isEmpty()) {
				continue;
			}

			for (Node v: adjNodes) {
				if (!visited.contains(v.toString())) {
					frontier.addFirst(v);
					if (problem.goalTest(v.getState())) {
						printSolution(v.path(), (int) v.getPathCost(), visited.size());
						return v;
					}
				}
			}

		}

		return null;

	}

	// Informed (Heuristic) Search
	
	public static Node aStarSearch(Problem problem){
		HashSet<String> visited = new HashSet<>();

		Comparator<Node> nodeComparator = new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return Double.compare(problem.h(o1) + o1.getPathCost(), problem.h(o2) + o2.getPathCost());
			}
		};

		PriorityQueue<Node> frontier = new PriorityQueue<>(nodeComparator);

		Node initialNode = new Node(problem.getInitial(), null, null, 0);

		frontier.add(initialNode);

		while (!frontier.isEmpty()) {
			Node top = frontier.poll();
			visited.add(top.toString());

			ArrayList<Node> adjNodes = top.expand(problem);

			if (adjNodes.isEmpty()) {
				continue;
			}

			for (Node v: adjNodes) {
				if (!visited.contains(v.toString())) {
					frontier.add(v);
					if (problem.goalTest(v.getState())) {
						printSolution(v.path(), v.getPathCost(), visited.size());
						return v;
					}
				}
			}
		}

		return null;
	}

	public static void printSolution(ArrayList<Node> path, double totalCost, int numNodesVisited){
		System.out.println("Solution path:");
		if (path == null){
			System.out.println("No solution found.");
			return;
		}
		for(Node n:path){
			System.out.print(n + " --> ");
		}
		System.out.println("\nTotal cost: "+totalCost);
		System.out.println("Number of nodes visited: "+numNodesVisited);
	}



	
	// Main
	public static void main(String[] args) throws FileNotFoundException{

		if (args.length < 3) {
			System.out.println("Usage: java search.Search <problem> <method> <initial state> <optional: goal state> <optional: threshold distance>");
			return;
		}

		
		// String problemString = "london";
		// String method = "bfs";
		// String initialState = "Piccadilly Circus";
		// String goalState = "Wimbledon";
		// boolean useThreshold = false;
		// double thresholdDistance = -1;
		String problemString = args[0].toLowerCase();
		String method = args[1].toLowerCase();
		String initialState = args[2].replaceAll("\"", "");
		String goalState = null;
		boolean useThreshold = false;
		double thresholdDistance = -1;

		if (args.length >= 4) {
			goalState = args[3].replaceAll("\"", "");
		}
		if (args.length >= 5) {
			useThreshold = true;
			thresholdDistance = Double.parseDouble(args[4]);
		}

		Problem problem;

		if (problemString.equals("boston")) {
			if (useThreshold) {
				problem = new SubwayNavigationProblemNear(initialState, goalState, SubwayMap.buildBostonMap(), thresholdDistance);
			} else {
				problem = new SubwayNavigationProblem(initialState, goalState, SubwayMap.buildBostonMap());
			}
		} else if (problemString.equals("london")) {
			if (useThreshold) {
				problem = new SubwayNavigationProblemNear(initialState, goalState, SubwayMap.buildLondonMap(), thresholdDistance);
			} else {
				problem = new SubwayNavigationProblem(initialState, goalState, SubwayMap.buildLondonMap());
			}
		} else if (problemString.equals("eight")) {
			problem = new EightProblem(initialState);
		} else {
			System.out.println("Invalid problem");
			return;
		}

		if (method.equals("bfs")) {
			breadthFirstSearch(problem);
		} else if (method.equals("dfs")) {
			depthFirstSearch(problem);
		} else if (method.equals("astar")) {
			aStarSearch(problem);
		} else {
			System.out.println("Invalid method");
			return;
		}
	}
}