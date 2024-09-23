"use client";
import { useParams ,useRouter} from 'next/navigation';
import { useState, useEffect } from 'react';
import SearchBar from '@/app/components/SearchBox';
import Star from '../../star/page';



interface Restaurant {
    id: number;
    name: string;
    type: string;
    address: string;
    tel: string;
    rate: number;
    menu: string;
    thumbnailImageUrl: string;
    subImageUrl: string;
    lat: number;
    lng: number;
}

const RestaurantDetail: React.FC = () => {
    const { id } = useParams();
    const [restaurant, setRestaurant] = useState<Restaurant | null>(null);
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState<string>('');
    const [filteredRestaurants, setFilteredRestaurants] = useState<Restaurant[]>([]);


    const [allAverage, setAllAverage] = useState<number | null>(null);
    const [tags, setTags] = useState<string[]>([]);
    const router = useRouter();


useEffect(() => {
    const fetchData = async () => {
        try {
            
            const [restaurantRes, allAverageRes, tagsRes] = await Promise.all([
                fetch(`http://localhost:8080/api/restaurant/${id}`),
                fetch(`http://localhost:8080/api/posts/${id}/allAverage`),
                fetch(`http://localhost:8080/api/tags/top5/${id}`)
            ]);
            
            
            const restaurantData = await restaurantRes.json();
            const allAverageData = await allAverageRes.json(); 
            const tagsData = await tagsRes.json();

            
            setRestaurant(restaurantData);
            setAllAverage(allAverageData);
            setTags(tagsData);
        } catch (error) {
            console.error("Error fetching data:", error);
        } finally {
            setLoading(false);
        }
    };

    if (id) {
        fetchData();
    }
}, [id]);


   
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

                        geocoder.addressSearch(restaurant.address, function(result: any, status: any) {
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

    const handleSearch = async (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchTerm(e.target.value);

        if (e.target.value) {
            const res = await fetch(`http://localhost:8080/api/restaurant/search?q=${e.target.value}`);
            const data = await res.json();
            setFilteredRestaurants(data);
        } else {
            setFilteredRestaurants([]);
        }
    };

    if (loading) return <div>Loading...</div>;
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
            <div className="mb-6">
                <SearchBar searchTerm={searchTerm} onSearch={handleSearch} />
            </div>

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
                    <div id="map" style={{ width: '100%', height: '350px' }}></div>
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
                    <p><strong>네이버 평점:</strong>
                    <div className="flex items-center">
                        <Star w="w-6" h="h-6" readonly={true} rate={restaurant.rate} onChange={() => { }} />
                        <p className="ml-2">{restaurant.rate.toFixed(1)} / 5</p>
                    </div> </p>
                    <p><strong>메뉴:</strong></p>
                    <div className="whitespace-pre-line">
                        {renderMenu(restaurant.menu)}
                    </div>
                </div>
            )}
            <div className="mb-4">
                <strong>[레스토랑 전체 평점]</strong>
                {allAverage !== null ? (
                    <div className="flex items-center">
                        <Star w="w-6" h="h-6" readonly={true} rate={allAverage} onChange={() => { }} />
                        <p className="ml-2">{allAverage.toFixed(1)} / 5</p>
                    </div>
                ) : (
                    '등록된 평점이 없습니다.'
                )}
            </div>

            <div className="mb-4">
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
            <div>
                <button className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded"
                    onClick={()=> router.push(`/post/${id}`)}>
                    후기 보기
                </button>
            </div>
        </div>
    );
};

export default RestaurantDetail;
