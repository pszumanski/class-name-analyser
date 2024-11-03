const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const getLimitInfo = () => (
    fetch(`${backendUrl}/rate-limit`)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Failed to connect to backend');
            }
        })
        .catch(error => {
            console.log('TODO: redirect to error page');
        })
);