const URLGames = "http://localhost:8080/games";
const URLPublisher = "http://localhost:8080/publisher";

// GAMES

// Gets all Games from the database
function fetchGame() {
    const fetchData = { method: "get" };

    // Fetch data from the specified URL
    fetch(URLGames, fetchData)
        .then(response => response.json())
        .then(data => {
            if (Object.keys(data).length > 0) {
                // Parse the response data into JSON format
                jsonData = JSON.parse(JSON.stringify(data));

                // Clear the table body
                var tableBody = document.getElementById("games-table-body");
                tableBody.innerHTML = "";

                // Iterate through each Game object
                jsonData.forEach((gamesObj) => {
                    const bodyRow = document.createElement('tr');

                    // Create table cells for each property of the Game
                    for (const key in gamesObj) {
                        const td = document.createElement('td');
                        td.style.cursor = "pointer";

                        // Set the content of each column based on its key
                        if (key == "id") {
                            // ID column is non-editable
                            td.innerHTML = '<p contenteditable="false">' + gamesObj[key] + '</p>';
                            bodyRow.id = gamesObj[key];
                            td.style.cursor = "default";
                        } else {
                            // Other columns are editable
                            td.id = bodyRow.id + key + "-table-games";
                            const editableTableColumn = document.createElement('p');
                            editableTableColumn.className = "table-col";
                            editableTableColumn.contentEditable = true;
                            editableTableColumn.innerHTML = gamesObj[key];
                            const originalValue = gamesObj[key];

                            // Add an event listener to detect changes and trigger an update
                            editableTableColumn.addEventListener("blur", function () {
                                const newValue = this.innerHTML.trim();
                                if (newValue !== originalValue) {
                                    updateGame(bodyRow.id);
                                }
                            });
                            td.appendChild(editableTableColumn);
                        }
                        bodyRow.appendChild(td);
                    }

                    // Create a delete button for each row
                    const td = document.createElement('td');
                    const deleteButton = document.createElement("button");
                    deleteButton.type = "button";
                    deleteButton.className = "btn btn-danger";
                    deleteButton.innerHTML = '<i contenteditable="false" class="bi bi-trash-fill"></i>';
                    deleteButton.addEventListener("click", () => {
                        deleteGame(bodyRow.id);
                    });
                    td.appendChild(deleteButton);
                    bodyRow.appendChild(td);
                    tableBody.appendChild(bodyRow);
                });
            }
        });
}

// Create a new Game
function createGame() {

    const fetchData = {
        method: "post",
        body: JSON.stringify({
            "id": document.getElementById("id").value,
            "gameName": document.getElementById("gameName").value,
            "publisher_id": document.getElementById("publisher_id").value,
            "releaseDate": document.getElementById("releaseDate").value,
            "platform": document.getElementById("platform").value
        }),
        headers: {
            "Content-type": "application/json"
        }
    };

    // Send a POST request to create a new Game
    fetch(URLGames, fetchData)
        .then(response => {
            if(response.status != 500){
                fetchGame();
            }
        });
}

// Delete a Game by ID
function deleteGame(id) {
    if(confirm("Willst du es wirklich löschen?")) {

        const fetchData = {
            method: "delete",
        };

        // Send a DELETE request to delete the specified Game
        fetch(URLGames + "/" + id, fetchData)
            .then(data => {
                // Refresh the table after deleting a Game
                fetchGame();
            })
    }
}

// Update a Game
function updateGame(id) {
    const fetchData = {
        method: "put",
        body: JSON.stringify({
            "id": id,
            "gameName": document.getElementById(id + "gameName-table-games").textContent,
            "publisher_id": document.getElementById(id + "publisher_id-table-games").textContent,
            "releaseDate": document.getElementById(id + "releaseDate-table-games").textContent,
            "platform": document.getElementById(id + "platform-table-games").textContent
        }),
        headers: {
            "Content-type": "application/json"
        }
    };

    // Send a PUT request to update the Game
    fetch(URLGames, fetchData)
        .then(response => {
            // Refresh the table after updating the Game
            fetchGame();
        })
}


// PUBLISHER

// Gets all Publishers from the database
function fetchPublisher() {
    const fetchData = { method: "get" };

    // Fetch data from the specified URL
    fetch(URLPublisher, fetchData)
        .then(response => response.json())
        .then(data => {
            if (Object.keys(data).length > 0) {
                // Parse the response data into JSON format
                jsonData = JSON.parse(JSON.stringify(data));

                // Clear the table body
                var tableBody = document.getElementById("publisher-table-body");
                tableBody.innerHTML = "";

                // Iterate through each Publisher object
                jsonData.forEach((publisherObj) => {
                    const bodyRow = document.createElement('tr');

                    // Create table cells for each property of the Publisher
                    for (const key in publisherObj) {
                        const td = document.createElement('td');
                        td.style.cursor = "pointer";
                        td.id = publisherObj[key] + "-" + key + "-table-publisher";

                        // Set the content of each column based on its key
                        if (key == "id") {
                            // ID column is non-editable
                            td.innerHTML = '<p contenteditable="false">' + publisherObj[key] + '</p>';
                            bodyRow.id = publisherObj[key];
                            td.style.cursor = "default";
                        } else {
                            // Other columns are editable
                            td.id = bodyRow.id + key + "-table-publisher";
                            const editableTableColumn = document.createElement('p');
                            editableTableColumn.className = "table-col";
                            editableTableColumn.contentEditable = true;
                            editableTableColumn.innerHTML = publisherObj[key];
                            const originalValue = publisherObj[key];

                            // Add an event listener to detect changes and trigger an update
                            editableTableColumn.addEventListener("blur", function () {
                                const newValue = this.innerHTML.trim();
                                if (newValue !== originalValue) {
                                    updatePublisher(bodyRow.id);
                                }
                            });
                            td.appendChild(editableTableColumn);
                        }
                        bodyRow.appendChild(td);
                    }

                    // Create a delete button for each row
                    const td = document.createElement('td');
                    const deleteButton = document.createElement("button");
                    deleteButton.type = "button";
                    deleteButton.className = "btn btn-danger";
                    deleteButton.innerHTML = '<i contenteditable="false" class="bi bi-trash-fill"></i>';
                    deleteButton.addEventListener("click", () => {
                        deletePublisher(bodyRow.id);
                    });
                    td.appendChild(deleteButton);
                    bodyRow.appendChild(td);
                    tableBody.appendChild(bodyRow);
                });
            }
        });
}

// Create a new Publisher
function createPublisher() {

    const fetchData = {
        method: "post",
        body: JSON.stringify({
            "id": document.getElementById("id-publisher").value,
            "publisherName": document.getElementById("publisherName").value,
        }),
        headers: {
            "Content-type": "application/json"
        }
    };

    // Send a POST request to create a new Publisher
    fetch(URLPublisher, fetchData)
        .then(response => {
            if(response.status != 500){
                fetchPublisher();
            }
        });
}

// Delete a Publisher by ID
function deletePublisher(id) {
    if(confirm("Willst du es wirklich löschen?")) {

        const fetchData = {
            method: "delete",
        };

        // Send a DELETE request to delete the specified Publisher
        fetch(URLPublisher + "/" + id, fetchData)
            .then(data => {
                // Refresh the table after deleting a Publisher
                fetchPublisher();
            })
    }
}

// Update a Publisher
function updatePublisher(id) {

    const fetchData = {
        method: "put",
        body: JSON.stringify({
            "id": id,
            "publisherName": document.getElementById(id + "publisherName-table-publisher").textContent,
        }),
        headers: {
            "Content-type": "application/json"
        }
    };

    // Send a PUT request to update the Publisher
    fetch(URLPublisher, fetchData)
        .then(response => {
            // Refresh the table after updating the Publisher
            fetchPublisher();
        })
}
