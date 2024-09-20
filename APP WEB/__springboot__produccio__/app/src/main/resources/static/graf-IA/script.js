window.onload = loadJSON;


function loadJSON() {
    fetch('prova.json')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log(data); // Aquí puedes procesar los datos del JSON
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
    }