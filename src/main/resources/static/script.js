/**
 * This will fetch data as a stream
 */
const fetchData = async () => {
    try {
        const response = await fetch('http://localhost:8080/api/v1/stream/employee/all');

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        // Instantiating a reader for the stream
        const reader = response.body.getReader();

        // This flag will check when to complete it
        let finished = false;
        let data = '';
        while (!finished) {
            const {done, value} = await reader.read();
            finished = done;

            data += new TextDecoder().decode(value);
            // This was the token if you remember
            const array = data.split('#');

            // We do this, because chunk won't always have full data
            // sometime it will be cut off, to handle that scenario
            data = array[array.length - 1];
            for (let i = 0; i < array.length - 1; i++) {
                console.log(JSON.parse(array[i]));
            }
        }
    } catch (error) {
        console.error('Error fetching data:', error.message);
    }
}

/**
 * Normal fetch call
 */
const fetchDataNormal = async () => {
    try {
        const response = await fetch('http://localhost:8080/api/v1/normal/employee/all');

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const employees = await response.json();

        console.log(employees);
    } catch (error) {
        console.error('Error fetching data:', error.message);
    }
}

// Add event listener to buttons
function main() {
    document.getElementById('stream-button').addEventListener('click', async () => {
        await fetchData();
    });
    document.getElementById('normal-button').addEventListener('click', async () => {
        await fetchDataNormal();
    });
}

// DOM load
if (document.readyState !== 'loading') {
    main();
} else {
    document.addEventListener('DOMContentLoaded', main);
}