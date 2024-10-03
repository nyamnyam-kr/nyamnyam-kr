"use client";
import { useState, useEffect } from 'react';
import Link from 'next/link';
import Sidebar from 'src/app/components/SideBar';
import ScrollToTop from 'src/app/components/ScrollToTop';
import { SearchProvider, useSearchContext } from 'src/app/components/SearchContext';
import { getRestaurantsByCategory, getRestaurantsBySearch, getRestaurantsByTag } from 'src/app/service/restaurant/restaurant.service';
import HeartButton from 'src/app/components/AddHeart';
import Modal from "src/app/components/Modal";
import { fetchRestaurantOne } from 'src/app/service/restaurant/restaurant.service'; // 함수 불러오기

export default function Home() {
    const [restaurants, setRestaurants] = useState<RestaurantModel[]>([]);
    const { searchTerm } = useSearchContext();
    const [selectedTags, setSelectedTags] = useState<string[]>([]);
    const [selectedCategories, setSelectedCategories] = useState<string[]>([]);
    const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
    const [modalRestaurant, setModalRestaurant] = useState<RestaurantModel | null>(null); // 모달에서 보여줄 레스토랑 데이터

    useEffect(() => {

        const loadRestaurant = async () => {
            try {
                const restaurantData = await fetchRestaurantOne(8);
                console.log(restaurantData);
                setModalRestaurant(restaurantData);
                setIsModalOpen(true);
            } catch (error) {
                console.error("Error fetching restaurant:", error);
                setIsModalOpen(false);
            }
        };

        loadRestaurant();
    }, []);

    const handleFilterChange = (tags: string[], categories: string[]) => {
        setSelectedTags(tags);
        setSelectedCategories(categories);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                let data: RestaurantModel[] = await getRestaurantsBySearch(searchTerm || '');

                if (selectedTags.length > 0) {
                    const tagData = await getRestaurantsByTag(selectedTags);
                    data = data.filter(restaurant => tagData.some((tagged: { id: number; }) => tagged.id === restaurant.id));
                }

                if (selectedCategories.length > 0) {
                    const categoryData = await getRestaurantsByCategory(selectedCategories);
                    data = data.filter(restaurant => categoryData.some((categorized: { id: number; }) => categorized.id === restaurant.id));
                }

                setRestaurants(data);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, [searchTerm, selectedTags, selectedCategories]);




    return (
        <div className="overflow-y-auto">
            <SearchProvider>
                <div className="container mx-auto px-4 py-4 flex">
                    <div className="w-64">
                        <Sidebar onFilterChange={handleFilterChange} />
                    </div>
                    <div className="flex-grow ml-4">
                        {restaurants.length > 0 ? (
                            <ul className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                                {restaurants.map((restaurant) => (
                                    <li key={restaurant.id} className="bg-white shadow-md rounded-lg overflow-hidden relative">
                                        <Link href={`/restaurant/${restaurant.id}`}>
                                            <img
                                                src={restaurant.thumbnailImageUrl || '/default-thumbnail.jpg'}
                                                alt={restaurant.name}
                                                className="w-full h-48 object-cover"
                                            />
                                        </Link>
                                        <div className="absolute top-2 right-2">
                                            <HeartButton restaurantId={restaurant.id} />
                                        </div>
                                        <Link href={`/restaurant/${restaurant.id}`}>
                                            <div className="p-4">
                                                <h2 className="text-xl font-bold mb-2">{restaurant.name}</h2>
                                                <p className="text-gray-600">유형: {restaurant.type}</p>
                                                <p className="text-gray-600">주소: {restaurant.address}</p>
                                                <p className="text-gray-600">전화번호: {restaurant.tel}</p>
                                                <p className="text-gray-600">평점: {restaurant.rate}</p>
                                            </div>
                                        </Link>
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p className="text-gray-600">조건에 맞는 결과가 존재하지 않습니다</p>
                        )}
                    </div>
                </div>

                <ScrollToTop />

                {/* Modal 컴포넌트 사용 */}
                <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}>
                    {modalRestaurant ? (
                        <div className={"text-center"}>
                            <h5></h5>
                            <h1>오늘 이 음식점 어때요?</h1>
                            <h2 className="text-xl font-bold">{modalRestaurant.name}</h2>

                                <img
                                    src={modalRestaurant.thumbnailImageUrl || '/default-thumbnail.jpg'}
                                    alt={modalRestaurant.name}
                                    className="w-full h-48 object-cover"
                                />
                            <p className="mt-2">주소: {modalRestaurant.address}</p>
                            <p className="mt-2">전화번호: {modalRestaurant.tel}</p>
                            <p className="mt-2">유형: {modalRestaurant.type}</p>
                            <Link href={`/restaurant/${modalRestaurant.id}`}><button className="mt-4 bg-orange-400 text-white py-2 px-4 rounded">음식점으로 이동</button></Link>

                        </div>

                    ) : (
                        <p>로딩 중...</p>
                    )}
                </Modal>
            </SearchProvider>
        </div>
    );
}
