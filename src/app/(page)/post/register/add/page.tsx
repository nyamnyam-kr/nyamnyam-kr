// Home.tsx
"use client";
import { useState, useEffect } from "react";
import Link from "next/link";

import axios from "axios";
import { useRouter } from "next/navigation";
import Star from "@/app/(page)/star/page";
import SearchBar from "@/app/components/SearchBox";


const fetchRestaurants = async (keyword: string = ""): Promise<RestaurantModel[]> => {
  const res = await fetch(`http://localhost:8080/api/restaurant/search?q=${keyword}`);
  const data = await res.json();
  return data;
};

export default function Home() {
  const [restaurants, setRestaurants] = useState<RestaurantModel[]>([]);
  const [searchTerm, setSearchTerm] = useState<string>("");

  useEffect(() => {
    const getRestaurants = async () => {
      const data = await fetchRestaurants(searchTerm);
      setRestaurants(data);
    };
    getRestaurants();
  }, [searchTerm]);

  const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  };

  return (
    <div className="container mx-auto p-4">
      <div className="mb-6">
        <SearchBar searchTerm={searchTerm} onSearch={handleSearch} />
      </div>

      {restaurants.length > 0 ? (
        <ul className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-6">
          {restaurants.map((restaurant) => (
            <li key={restaurant.id} className="bg-white shadow-md rounded-lg overflow-hidden">
              <RestaurantDetailCard restaurant={restaurant} />
            </li>
          ))}
        </ul>
      ) : (
        <p className="text-gray-600">No restaurants found matching your search criteria.</p>
      )}
    </div>
  );
}

function RestaurantDetailCard({ restaurant }: { restaurant: RestaurantModel }) {
  const [allAverage, setAllAverage] = useState<number | null>(null);
  const [tags, setTags] = useState<string[]>([]);

  useEffect(() => {
    const fetchAllAverage = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/posts/${restaurant.id}/allAverage`);
        setAllAverage(response.data);
      } catch (error) {
        console.error("전체 평점 오류: ", error);
      }
    };

    const fetchTagRestaurant = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/tags/top5/${restaurant.id}`);
        setTags(response.data);
      } catch (error) {
        console.error("top5 태그 불러오기 오류: ", error);
      }
    };

    fetchAllAverage();
    fetchTagRestaurant();
  }, [restaurant.id]);

  return (
    <div>
      <Link href={`/restaurant/${restaurant.id}`}>
        <img
          src={restaurant.thumbnailImageUrl || "/default-thumbnail.jpg"}
          alt={restaurant.name}
          className="w-full h-48 object-cover"
        />
        <div className="p-4">
          <h2 className="text-xl font-bold mb-2">{restaurant.name}</h2>
          <p className="text-gray-600">유형: {restaurant.type}</p>
          <p className="text-gray-600">주소: {restaurant.address}</p>
          <p className="text-gray-600">전화번호: {restaurant.tel}</p>
          <p className="text-gray-600">평점: {restaurant.rate}</p>

          <div className="mb-2">
            <strong>[레스토랑 전체 평점]</strong>
            {allAverage !== null ? (
              <div className="flex items-center">
                <Star w="w-6" h="h-6" readonly={true} rate={allAverage} onChange={() => {}} />
                <p className="ml-2">{allAverage.toFixed(1)} / 5</p>
              </div>
            ) : (
              "등록된 평점이 없습니다."
            )}
          </div>

          <div className="mb-2">
            <strong>[가장 많이 선택된 태그]</strong>
            {tags.length > 0 ? (
              <ul>
                {tags.map((tag, index) => (
                  <li key={index}>{tag}</li>
                ))}
              </ul>
            ) : (
              <p>등록된 태그가 없습니다.</p>
            )}
          </div>
        </div>
      </Link>
    </div>
  );
}
