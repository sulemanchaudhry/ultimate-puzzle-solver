# ultimate-puzzle-solver

Solving a 2x2 and 3x3 grid; refer to http://www.theultimatepuzzle.com/HowToPlay.html for puzzle information

For sample solutions, see https://ultimatepuzzle.wordpress.com/images/ (or The_Ultimate_Puzzle_Solutions.pdf)


TODO: Possible improvements.
- Consider re-writing comparison to use akka router to distribute checking across multiple cores; structure similar to:
https://codereview.stackexchange.com/questions/65039/distributing-workload-using-akka-router-with-exception-handling