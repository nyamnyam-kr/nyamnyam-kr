"use client";
import React, { useEffect, useState } from "react";



export default function Home2() {
    
    const [data, setData] = useState(null);
    const [name, setName] = useState(null);
    const [tel, setTel] = useState(null);




    useEffect(() => {
        fetch('http://211.188.50.43:8080/restaurant/findAll')
          .then((response) => {
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            return response.json();
          })
          .then((data) => {
            alert(JSON.stringify(data));
            setName(data[0].name)
            
          })
          .catch((error) => {
            console.error('There has been a problem with your fetch operation:', error);
          });
      }, []);

    return (
        <main className="flex min-h-screen flex-col items-center justify-between p-24">
            <table className="table-auto border border-indigo-600">
                <thead>
                    <tr className="border border-indigo-600">
                        <th>index</th>
                        <th>name</th>
                        <th>tel</th>
                    </tr>
                </thead>
                <tbody>
                    <tr className="border border-indigo-600">
                        <td>The Sliding Mr. Bones (Next Stop, Pottersville)</td>
                        <td>Malcolm Lockyer</td>
                        <td>1961</td>
                    </tr>
                    <tr className="border border-indigo-600">
                        <td>Witchy Woman</td>
                        <td>The Eagles</td>
                        <td>1972</td>
                    </tr>
                    <tr>
                        <td>Shining Star</td>
                        <td>Earth, Wind, and Fire</td>
                        <td>1975</td>
                    </tr>
                </tbody>
            </table>
            <pre>{JSON.stringify(data, null, 2)}</pre>
        </main>




    );
}

