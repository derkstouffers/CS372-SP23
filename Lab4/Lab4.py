# Deric Shaffer
# CS372 - Lab4
# Due Date: April 3rd, 2023

from typing import List
import time as t

class node:
    # acts as the private variable declarations for the node class
    def __init__(self, name, id):
        self.m_name = name
        self.m_id = id # unique integer from 0 to n - 1 where n = # of nodes
    
    def id(self):
        return self.m_id
    
    def name(self):
        return self.m_name

    
class graph:
    # acts as the private variable declarations for the graph class
    def __init__(self, file: str):
        self.m_nodes = [] # create a list to hold nodes
        self.m_adjList = [] # create an adjacency list to hold edges
        self.scan(file)
    
    # insert an edge (a,b) to m_adjList
    def addEdge(self, a: node, b: node):
        self.m_adjList[a.id()].append(b)
    
    # insert a node to m_nodes array
    def addNode(self, a: node):
        # if arrays are empty, fill them
        while len(self.m_nodes) <= a.id():
            self.m_nodes.append(None)
            self.m_adjList.append([])

        self.m_nodes[a.id()] = a

    # return node with id = i
    def getNode(self, i: int) -> node:
        return self.m_nodes[i]

    # return reference to adjacency list of node
    def getAdjNodes(self, a: node) -> List[node]:
        return self.m_adjList[a.id()]

    # return number of nodes
    def num_nodes(self) -> int:
        return len(self.m_nodes)
    
    # create a graph from a tab-separated text edge list file to adjacency list
    def scan(self, file: str):
        with open(file, "r") as f:
            i = 0 # counter variable for node id's
            existing_nodes = {} # helper dictionary to keep track of if the name of a node & its id in the file already exists or not
            for line in f:
                # convert characters in file into strings and map them to a and b
                a, b = map(str, line.strip().split('\t'))

                # if first node (a) in line doesn't exist, create and add the node
                if a not in existing_nodes:
                    node_1 = node(a, i)
                    self.addNode(node_1)
                    existing_nodes[a] = i
                    i += 1

                # if second node (b) in line doesn't exist, create and add the node
                if b not in existing_nodes:
                    node_2 = node(b, i)
                    self.addNode(node_2)
                    existing_nodes[b] = i
                    i += 1

                # if first node (a) in line already exists, grab the node's id from the helper dictionary
                if a in existing_nodes:
                    node_1 = node(a, existing_nodes[a])
                
                # if second node (b) in line already exists, grab the node's id from the helper dictionary
                if b in existing_nodes:
                    node_2 = node(b, existing_nodes[b])

                # add the edge to adjacency list
                self.addEdge(node_1, node_2)


    
    # save a graph from adjacency lists to a tab-separated text edge list file
    def save(self, file: str):
        with open(file, "w") as f:
            for node in self.m_nodes:
                for adj_node in self.m_adjList[node.id()]:
                    f.write(f"{node.name()}\t{adj_node.name()}\n")

# 3.3
def explore(G, v, visited, visit_nums, count):
    visited[v.id()] = True

    visit_nums[v] = []

    # assign pre visit number
    visit_nums[v].append(count)

    for u in G.getAdjNodes(v):
        if not visited[u.id()]:
            count += 1
            count = explore(G, u, visited, visit_nums, count)

    # assign postvisit number
    count += 1
    visit_nums[v].append(count)

    print(v.name() + ": " + str(visit_nums[v]))

    return count



# 3.5
def dfs(G):
    pre_and_post = {} # helper dictionary that houses pre and post visit numbers
    counter = 1 # counter variable for pre and post visit number assignments
    visited = [False] * G.num_nodes() # create visited array and fill each index with the value False
    
    for v in G.m_nodes:
        if not visited[v.id()]:
            counter = explore(G, v, visited, pre_and_post, counter)
            

# generate 5 example graphs to test your code, try to represent cyclic/acyclic, directed/undirected
start1 = t.time()
g_1 = graph("graph_1.txt")
dfs(g_1) # directed
g_1.save("output_1.txt")
stop1 = t.time()
print("Graph 1 Runtime: " + str((stop1 - start1) * 1000)  + " milliseconds\n")

start2 = t.time()
g_2 = graph("graph_2.txt")
dfs(g_2) # undirected
g_2.save("output_2.txt")
stop2 = t.time()
print("Graph 2 Runtime: " + str((stop2 - start2) * 1000)  + " milliseconds\n")

start3 = t.time()
g_3 = graph("graph_3.txt")
dfs(g_3) # cyclic
g_3.save("output_3.txt")
stop3 = t.time()
print("Graph 3 Runtime: " + str((stop3 - start3) * 1000)  + " milliseconds\n")

start4 = t.time()
g_4 = graph("graph_4.txt")
dfs(g_4) # acyclic
g_4.save("output_4.txt")
stop4 = t.time()
print("Graph 4 Runtime: " + str((stop4 - start4) * 1000) + " milliseconds\n")

start5 = t.time()
g_5 = graph("graph_5.txt")
dfs(g_5) # graph 1 but with an extra node with only 1 edge
g_5.save("output_5.txt")
stop5 = t.time()
print("Graph 5 Runtime: " + str((stop5 - start5) * 1000) + " milliseconds")