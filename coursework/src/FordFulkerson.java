/*
 * FordFulkerson API
 * Copyright © 2021 Ammar Raneez. All Rights Reserved.
 */

import java.io.FileNotFoundException;

/**
 * FordFulkerson class, which will be used to implement the FordFulkerson Algorithm
 * @version 1.x March 6th 2021
 * @author Ammar Raneez | 2019163 | W1761196
 */
public class FordFulkerson {
    //*will hold the path, on how we reach each vertex*//
    private FlowEdge[] edgeTo;
    //*value of the max flow*//
    private int flowValue;

    /**
     * Initialize
     * @param flowNetwork - the FlowNetwork to perform on
     * @param source - networks source
     * @param target - networks target
     * @throws InterruptedException - thrown on Thread sleep()
     */
    public FordFulkerson(FlowNetwork flowNetwork, int source, int target) throws InterruptedException {
        //*start by initializing flow to 0*//
        this.flowValue = 0;

        //*Ford Fulkerson Algorithm*//
        while (hasAugmentingPath(flowNetwork, source, target)) {
            //*the maximum flow that can be pushed at a time*//
            double bottleneckCapacity = Integer.MAX_VALUE;

            //*TODO Comment n understand*//
            for (int v = target; v != source; v = edgeTo[v].otherEnd(v)) {
                bottleneckCapacity = Math.min(bottleneckCapacity, edgeTo[v].residualCapacity(v));
            }

            for (int v = target; v != source; v = edgeTo[v].otherEnd(v)) {
                edgeTo[v].addResidualFlow(v, (int) bottleneckCapacity);
            }

            System.out.println("Bottleneck Capacity: " + bottleneckCapacity);
            System.out.print("Flow Value Incrementing from: " + flowValue + " to: ");
            flowValue += bottleneckCapacity;
            System.out.println(flowValue);
            System.out.println("Through path: \n");
            Thread.sleep(1000);
        }
    }

    /**
     * @param flowNetwork - which flow network
     * @param source - network source
     * @param target - network target
     * @return whether there's an augmenting path
     */
    private boolean hasAugmentingPath(FlowNetwork flowNetwork, int source, int target) {
        edgeTo = new FlowEdge[flowNetwork.getNumberOfVertices()];
        return flowNetwork.breadthFirstSearch(source, edgeTo, target);
    }

    /**
     * @return graphs max flow
     */
    public int getFlowValue() {
        return flowValue;
    }
}
