# Sudoku Solver (Spring Boot) — Small practice project

This project is a compact Spring Boot application to practice:
- REST API design
- Session handling (server-side, per-session puzzle state)
- Stepwise solver techniques (apply one technique per API call)
- Java & Spring Boot project layout

Main features:
- Create a solving session with a 9x9 grid (0 for blank)
- Get current puzzle state including "possible numbers" per empty cell
- Advance one step: the server attempts techniques ordered from easiest to harder and applies the first that makes progress. Techniques implemented:
    - Single Candidate (Naked Single)
    - Last Remaining in Row
    - Last Remaining in Column
    - Last Remaining in Box

How to run
1. Import the project into IntelliJ IDEA as a Maven project.
2. Run `com.example.sudoku.SudokuApplication` or use `mvn spring-boot:run`.
3. Server starts on port 8080.

APIs
- POST /api/sessions
    - Body: `{ "grid": [[9x9 ints]] }` (0 means empty)
    - Response: `{ "sessionId": "<uuid>" }`

- GET /api/sessions/{sessionId}
    - Response: puzzle DTO with `grid` and `possibles` (9x9 of lists)

- POST /api/sessions/{sessionId}/step
    - Advances one step (applies first applicable technique). Returns:
        - technique: name or "none"
        - changed: true/false
        - puzzle: updated state
        - notes: textual notes about assignment(s)

Example curl
1) Create session
   curl -s -X POST http://localhost:8080/api/sessions -H "Content-Type: application/json" -d '{
   "grid": [
   [5,3,0,0,7,0,0,0,0],
   [6,0,0,1,9,5,0,0,0],
   [0,9,8,0,0,0,0,6,0],
   [8,0,0,0,6,0,0,0,3],
   [4,0,0,8,0,3,0,0,1],
   [7,0,0,0,2,0,0,0,6],
   [0,6,0,0,0,0,2,8,0],
   [0,0,0,4,1,9,0,0,5],
   [0,0,0,0,8,0,0,7,9]
   ]
   }'

2) Step once
   curl -s -X POST http://localhost:8080/api/sessions/{sessionId}/step

Notes and next steps
- This is intentionally simple. It uses in-memory session storage (ConcurrentHashMap). If you want sessions to survive restarts or multiple instances, plug in Redis and use Spring Session.
- The solver currently recomputes possibilities after each assignment. For performance you can implement incremental updates.
- Add more advanced techniques (naked pairs, pointing pairs, X-wing, backtracking) as you progress. For full solving add an optional backtracking solver run when deterministic techniques fail.
- Add unit tests for techniques and controller.

Happy hacking!