"use client";
import React, {useEffect, useState} from 'react';
import {useParams, useRouter} from 'next/navigation';
import Star from 'src/app/components/Star';



export default function showRestaurant() {
    const [restaurant, setRestaurant] = useState<RestaurantModel | null>(null);
    const [filteredRestaurants, setFilteredRestaurants] = useState<RestaurantModel[]>([]);
    const router = useRouter();


    const {restaurantId} = useParams();

    useEffect(() => {
        if (restaurantId) {
            fetch(`http://localhost:8080/api/restaurant/${restaurantId}`)
                .then((response) => {
                    if (!response.ok) {
                        throw new Error("Failed to fetch group details");
                    }
                    return response.json();
                })
                .then((data) => {
                    setRestaurant(data);
                    console.log(data); // 데이터가 정상적으로 로드된 후 로그 출력
                })
                .catch((error) => {
                    console.error("Fetch error:", error);
                });
        }
    }, [restaurantId]);

    useEffect(() => {
        console.log(restaurant); // restaurant이 업데이트될 때마다 로그 출력
    }, [restaurant]);

    useEffect(() => {
        if (restaurant && restaurant.address) {
            const mapScript = document.createElement('script');

            mapScript.async = true;
            mapScript.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=2352038d1f2d9450032dd17ae632df20&autoload=false&libraries=services`;

            document.head.appendChild(mapScript);

            const onLoadKakaoMap = () => {
                (window as any).kakao.maps.load(() => {
                    const mapContainer = document.getElementById('map');
                    if (mapContainer) {
                        const mapOption = {
                            center: new window.kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                            level: 3, // 지도의 확대 레벨
                        };
                        const map = new kakao.maps.Map(mapContainer, mapOption);
                        const geocoder = new (window as any).kakao.maps.services.Geocoder();

                        geocoder.addressSearch(restaurant.address, function (result: any, status: any) {
                            if (status === window.kakao.maps.services.Status.OK) {
                                const coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);
                                const marker = new window.kakao.maps.Marker({
                                    map: map,
                                    position: coords
                                });

                                const infowindow = new window.kakao.maps.InfoWindow({
                                    content: `<div style="width:150px;text-align:center;padding:6px 0;">${restaurant.name}</div>`
                                });
                                infowindow.open(map, marker);
                                map.setCenter(coords);
                            } else {
                                console.error("주소 검색 실패:", status);
                            }
                        });
                    } else {
                        console.error("지도 컨테이너가 존재하지 않습니다.");
                    }
                });
            };

            mapScript.addEventListener('load', onLoadKakaoMap);
        }
    }, [restaurant]);


    if (!restaurant) return <div>Restaurant not found.</div>;

    const renderMenu = (menu: string) => {
        const menuItems = menu.split(/(?<=\d원),/).map(item => item.trim()).filter(item => item.length > 0);

        return (
            <div className="space-y-2">
                {menuItems.map((item, index) => {
                    const [name, price] = item.split('-').map(part => part.trim());
                    return (
                        <div key={index}>
                            <span>{name}</span>
                            <span> - {price}</span>
                        </div>
                    );
                })}
            </div>
        );
    };

    return (
        <div className="container mx-auto p-4">
            {filteredRestaurants.length > 0 ? (
                <ul className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-6">
                    {filteredRestaurants.map((restaurant) => (
                        <li key={restaurant.id} className="bg-white shadow-md rounded-lg overflow-hidden">
                            <img
                                src={restaurant.thumbnailImageUrl || '/default-thumbnail.jpg'}
                                alt={restaurant.name}
                                className="w-full h-48 object-cover"
                            />
                            <div className="p-4">
                                <h2 className="text-xl font-bold mb-2">{restaurant.name}</h2>
                                <p className="text-gray-600">유형: {restaurant.type}</p>
                                <p className="text-gray-600">주소: {restaurant.address}</p>
                                <p className="text-gray-600">전화번호: {restaurant.tel}</p>
                                <p className="text-gray-600">평점: ★ {restaurant.rate} </p>
                            </div>
                        </li>
                    ))}
                </ul>
            ) : (
                <div>
                    <h1 className="text-3xl font-bold mb-4">{restaurant.name}</h1>
                    <div id="map" style={{width: '100%', height: '350px'}}></div>
                    <div className="flex justify-between mb-4">
                        <img
                            src={restaurant.thumbnailImageUrl || '/default-thumbnail.jpg'}
                            alt={restaurant.name}
                            className="w-1/2 h-48 object-cover"
                        />
                        {restaurant.subImageUrl && (
                            <img
                                src={restaurant.subImageUrl || '/default-subimage.jpg'}
                                alt={restaurant.name}
                                className="w-1/2 h-48 object-cover"
                            />
                        )}
                    </div>
                    <p><strong>유형:</strong> {restaurant.type}</p>
                    <p><strong>주소:</strong> {restaurant.address}</p>
                    <p><strong>전화번호:</strong> {restaurant.tel}</p>
                    <p><strong>이용하신 메뉴: </strong></p>
                    <div className="whitespace-pre-line">
                        {renderMenu(restaurant.menu)}
                    </div>
                </div>
            )}
            <div>
                <h1>이용하신 음식점의 정보가 맞나요?</h1>
                <button
                    className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
                    onClick={() => {
                        if (restaurantId) {
                            router.push(`/post/register/${restaurantId}`);
                        } else {
                            console.error("Restaurant ID is not available");
                        }
                    }}>
                    포스트 등록하기
                </button>
            </div>
        </div>

    );
}
