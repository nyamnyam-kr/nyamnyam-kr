import { useEffect, useState } from 'react';

export default function Home() {
  const [isSuccess, setIsSuccess] = useState(null);

  useEffect(() => {
    fetch('http://localhost:8080/api/restaurants')
      .then((response) => response.json())
      .then((data) => {
        setIsSuccess(data); // 
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
        setIsSuccess(null);
      });
  }, []);

  return (
    <div>
      <h1>Seoul Restaurants Data</h1>
      {isSuccess === null ? (
        <p>Loading...</p>
      ) : isSuccess ? (
        <p>Data successfully saved to the database!</p>
      ) : (
        <p>Failed to save data.</p>
      )}
    </div>
  );
}
