import java.util.*;
public class Problem extends Object {
    private PriorityQueue<Node> frontier;
    private Set<Node> explorer_set;


    public Problem() {
//        char[] charArray = input.toCharArray();
        this.frontier = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return Integer.compare(node1.f, node2.f);
            }
        });
        this.explorer_set = new HashSet<>();
    }

    public LinkedList<Node> get_path(Node target) {
        LinkedList<Node> path = new LinkedList<Node>();
        while (target != null) {
            path.add(target);
            target = target.parent;
        }
        return path;
    }

    public LinkedList<Node> reverse_path(LinkedList<Node> path) {
        LinkedList<Node> reversed = new LinkedList<Node>();
        for (int i = path.size() - 1; i >= 0; i--)
            reversed.add(path.get(i));
        return reversed;
    }

    public void printPath(LinkedList<Node> path) {
        System.out.print("0. ");
        System.out.println(path.get(0).state);
        for (int i = 1; i < path.size(); i++) {
            System.out.println("   " + path.get(i).action);
            System.out.println("   Total cost so far is: " + path.get(i).g);
            if (i != path.size() - 1) {
                System.out.print(i + ". ");
                System.out.println(path.get(i).state);
            } else {
                System.out.print("Solution found: ");
                System.out.print(path.get(i).state);
                System.out.println(", lowest cost possible is: " + path.get(i).g);
            }

        }
    }

    public Node insideQueue(Node node, PriorityQueue<Node> q) {
        for (Node ele : q) {
            if (Arrays.equals(ele.state, node.state)) {
                return ele;
            }
        }
        return null;
    }

    public boolean insideSet(Node node, Set<Node> h) {
        for (Node ele : h) {
            if (Arrays.equals(ele.state, node.state))
                return true;
        }
        return false;
    }


    public boolean search(Node start) {
        HashMap<Node, Node> path = new HashMap<Node, Node>();
        path.put(start, null);
        this.frontier.add(start);
        path.put(start, start);
        while (true) {
            if (frontier.isEmpty()) {
                System.out.println("failure");
                return false;
            }
            Node node = frontier.poll();
            ;
            if (node.ifSolution()) {
                printPath(reverse_path(get_path(node)));
                return true;
            }
            explorer_set.add(node);
            boolean[] actions = check_valid(node);
            for (int i = 0; i < actions.length; i++) {
                if (actions[i]) {
                    Node child = node.childNode(i, node.calculate_O_index());
                    Node is_inside_frontier = insideQueue(child, this.frontier);
                    boolean is_inside_explorer_set = insideSet(child, this.explorer_set);
                    if (!is_inside_explorer_set || is_inside_frontier == null) {
                        this.frontier.add(child);
                    } else {
                        if (is_inside_frontier != null) {
                            if (is_inside_frontier.f < child.f) {
                                this.frontier.remove(is_inside_frontier);
                                this.frontier.add(child);
                            }
                        }
                    }
                }
            }
        }
    }


    private boolean[] check_valid(Node node) {
        int index = node.calculate_O_index();
        boolean[] arr = new boolean[]{true, true, true, true};
        switch (index) {
            case 0:
                arr[1] = false;
                arr[3] = false;
                break;
            case 1:
                arr[3] = false;
                break;
            case 5:
                arr[2] = false;
                break;
            case 6:
                arr[0] = false;
                arr[2] = false;

        }
//        if (index == 0) {
//            arr[1] = false;
//            arr[3] = false;
//        }
//        if (index == 1) {
//            arr[3] = false;
//        }
//        if (index == 5) {
//            arr[2] = false;
//        }
//        if (index == 6) {
//            arr[0] = false;
//            arr[2] = false;
//        }
        return arr;
    }

    public boolean checkValidString(String str) {
        char[] input = str.toCharArray();
        short B = 0;
        short O = 0;
        short W = 0;
        if (input.length != 7)
            return false;
        for (int i = 0; i < 7; i++) {
            if (input[i] == 'W')
                W++;
            else if (input[i] == 'B')
                B++;
            else
                O++;
        }
        if (W == 3 && B == 3 && O == 1)
            return true;
        return false;
    }

    public static void main(String[] args) {
        Problem p = new Problem();
        Scanner sc = new Scanner(System.in);
        boolean play = true;
        while (play) {
            System.out.print("Enter a string, or press 'q' if you want to quit");
            String str = sc.nextLine();
            if(str.equals("q")){
                System.out.println("END");
                break;
            }
            System.out.println(str);
            if (p.checkValidString(str))
                p.search(new Node(str.toCharArray(), null, 0, ""));
            else
                System.out.println("NOT VALID STRING");

        }
    }
}
