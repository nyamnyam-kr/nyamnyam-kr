"use client";
import React, { useState, ChangeEvent, FormEvent } from 'react';
import { useRouter } from 'next/navigation';
import { save } from'@/app/service/restaurant/restaurant.api';


export default function Home2() {

    const [restaurant, setRestaurant] = useState<RestaurantModel>({id: 0, name: '', tel: 0, address: '' });
    const router = useRouter();

    const [data, setData] = useState();

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { id, value } = event.target;
        const newValue = id === 'tel' ? parseFloat(value) : value;
        setRestaurant(prevRestaurant => ({ ...prevRestaurant, [id]: value }));
    };






    const handleSubmit = (event) => {
        event.preventDefault();
        console.log('Submitted Restaurant:', restaurant);
        // Perform API call or other actions here
        alert(`제출된 내용:\n이름: ${restaurant.name}\n전화번호: ${restaurant.tel}\n주소: ${restaurant.address}`);
        save(restaurant);
        router.push('/');
    };

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-md bg-white shadow-lg rounded-lg p-6">
                <h1 className="text-2xl font-bold mb-6 text-center">레스토랑 등록</h1>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label htmlFor="name" className="block text-sm font-medium text-gray-700">이름:</label>
                        <input
                            type="text"
                            id="name"
                            value={restaurant.name}
                            onChange={handleChange}
                            required
                            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="tel" className="block text-sm font-medium text-gray-700">전화번호:</label>
                        <input
                            type="number"
                            id="tel"
                            value={restaurant.tel}
                            onChange={handleChange}
                            required
                            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="address" className="block text-sm font-medium text-gray-700">주소:</label>
                        <input
                            type="text"
                            id="address"
                            value={restaurant.address}
                            onChange={handleChange}
                            required
                            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full py-2 px-4 bg-blue-500 text-white font-semibold rounded-md shadow-sm hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-300"
                    >
                        제출
                    </button>
                </form>
            </div>
        </main>
    );
}