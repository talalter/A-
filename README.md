# A-star-Algorithm
Given a rectangle containing 7 squares - 3 whites, 3 blacks , 1 empty.
the target is to move all the black squares to the right of the white squares, when the location of the empty square is not significant.
Permitted steps are:
Switching between a painted square and an empty square provided that it is one sqaure or two
squares away from it - the cost of a step is coressponding to a distace of 1 or 2.

The heuristic function is - 
The number of white squares to the left of which is one or more black squares.


The rectange is represent by String with length 7:

3 - 'W'  represent white square

3 - 'B'  represent black square

1 - 'O'  represent empty square


input : WBWBWOB
output : Solution found- WWWBOBB, lowest cost possible is: 7
