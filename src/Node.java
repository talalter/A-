import java.util.Comparator;
import java.util.PriorityQueue;

public class Node {

    char [] state;
    Node parent;
    int g;
    int h;
    int f;
    String action = "";
    public Node(char [] state,Node parent,int g,String action){
        this.state=state;
        this.parent=parent;
        this.g=g;
        this.h=calculate_h(state);
        this.f = this.g+this.h;
        this.action=action;

    }
    public boolean ifSolution() {
        boolean opt1 = (this.state[0]=='W' && this.state[1]=='W' && this.state[2]=='W');
        if (opt1) {
            return true;
        }
        for (int i=0;i<4;i++){
            if(state[i]=='B'){
                return false;
            }
        }
        return true;
    }
    public int calculate_O_index() {
        for (int i = 0; i < this.state.length; i++) {
            if (this.state[i] == 'O') {
                return i;
            }
        }
        System.out.println("mistake");
        return -1;
    }
    //action is a number indicates : 1-switch one index from laft, 2-switch 2 indexes from laft, 3- switch 1 from right, 4-.
    public Node childNode(int action, int index) {
        char [] state = this.state.clone();
        String action_string;
        action_string="";
        int new_g=this.g;
        if(action==0){
            state[index]=state[index+1];
            state[index+1]='O';
            new_g=1+this.g;
            action_string = "switching "+state[index]+" in index "+(index+1)+" and blank in index "+index+"\n   Cost of this action is: "+ 1;
        }
        if(action==1){
            state[index]=state[index-1];
            state[index-1]='O';
            new_g=1+this.g;
            action_string = "switching "+state[index]+" in index "+(index-1)+" and blank in index "+index+"\n   Cost of this action is: "+ 1;

        }
        if(action==2){
            state[index]=state[index+2];
            state[index+2]='O';
            new_g=2+this.g;
            action_string = "switching "+state[index]+" in index "+(index+2)+" and blank in index "+index+"\n   Cost of this action is: "+ 2;

        }
        if(action==3){
            state[index]=state[index-2];
            state[index-2]='O';
            new_g=2+this.g;
            action_string = "switching "+state[index]+" in index "+(index-2)+" and blank in index "+index+"\n   Cost of this action is: "+ 2;

        }
        Node child = new Node(state,this,new_g,action_string);
        return child;
    }

    public int calculate_h(char [] state){
        int count=0;
        for(int i=state.length-1;i>0;i--){
            if(state[i]=='W'){
                for(int j=i-1;j>0;j--){
                    if(state[j]=='B'){
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
    }

    public static void main (String [] args){
        String str = "BOBWWBW";
        char[] c = str.toCharArray();
//        Node n1 = new Node(charArray,null,0,"");
        PriorityQueue<Node> frontier = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return Integer.compare(node1.g, node2.g);
            }
        });
        Node n1 = new Node(c,null,1,"11");
        Node n2 = new Node(c,null,3,"33");
        Node n3 = new Node(c,null,2,"22");
        Node n4 = new Node(c,null,4,"44");
        frontier.add(n1);
        frontier.add(n2);
        frontier.add(n3);
        frontier.add(n4);
        while(!frontier.isEmpty()){
            System.out.println(frontier.poll().action);
        }

    }
}
