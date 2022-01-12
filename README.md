# ultimate-puzzle-solver

Solving a 2x2 and 3x3 grid; original game website no longer available (see https://web.archive.org/web/20180822063807/http://www.theultimatepuzzle.com/HowToPlay.html or HowToPlay.pdf); game is akin to a jigsaw puzzle, but joining in upto four directions according to the shapes on each side of the square.
See also  https://www.ebay.com/itm/324915228343?hash=item4ba674eeb7:g:ALIAAOSwRA5hpxeX (or ultimate_puzzle_1.jpg, ultimate_puzzle_2.jpg)

For sample solutions, see https://ultimatepuzzle.wordpress.com/images/ (or The_Ultimate_Puzzle_Solutions.pdf)


TODO: Possible improvements.
- Consider re-writing comparison to use akka router to distribute checking across multiple cores; structure similar to:
https://codereview.stackexchange.com/questions/65039/distributing-workload-using-akka-router-with-exception-handling
