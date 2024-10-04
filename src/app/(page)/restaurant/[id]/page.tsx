"use client";
import { useParams, useRouter } from 'next/navigation';
import { useState, useEffect } from 'react';
import { useSearchContext } from 'src/app/components/SearchContext';
import { getRestaurantDetails } from 'src/app/service/restaurant/restaurant.service';
import Star from '../../../components/Star';



export default function Restaurant() {
    const { id } = useParams();
    const [restaurant, setRestaurant] = useState<RestaurantModel | null>(null);
    const [loading, setLoading] = useState(true);
    const [filteredRestaurants, setFilteredRestaurants] = useState<RestaurantModel[]>([]);
    const [allAverage, setAllAverage] = useState<number | null>(null);
    const [tags, setTags] = useState<string[]>([]);
    const router = useRouter();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const { restaurants, allAverage, tags } = await getRestaurantDetails(Number(id));
                setRestaurant(restaurants);
                setAllAverage(allAverage);
                setTags(tags);
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
                            center: new window.kakao.maps.LatLng(33.450701, 126.570667),
                            level: 3,
                        };
                        const map = new kakao.maps.Map(mapContainer, mapOption);
                        const geocoder = new (window as any).kakao.maps.services.Geocoder();

                        geocoder.addressSearch(restaurant.address, function (result: any, status: any) {
                            if (status === window.kakao.maps.services.Status.OK) {
                                const coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);
                                const marker = new window.kakao.maps.Marker({
                                    map: map,
                                    position: coords,
                                });
                                const infowindow = new window.kakao.maps.InfoWindow({
                                    content: `<div style="width:100%;padding:6px 0;">${restaurant.name}</div>`,
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



    if (loading) return <div className="text-center py-4">Loading...</div>;
    if (!restaurant) return <div className="text-center py-4">Restaurant not found.</div>;

    const renderMenu = (menu: string) => {
        const menuItems = menu.split(/(?<=\d원),/).map(item => item.trim()).filter(item => item.length > 0);

        return (
            <div className="space-y-2">
                {menuItems.map((item, index) => {
                    const [name, price] = item.split('-').map(part => part.trim());
                    return (
                        <div key={index} className="flex justify-between">
                            <span className="text-gray-800 text-lg">{name}</span>
                            <span className="text-gray-600 text-lg">{price}</span>
                        </div>
                    );
                })}
            </div>
        );
    };

    const renderOperTime = (operTime: string) => {
        const timeItems = operTime.match(/(월|화|수|목|금|토|일|매일)\s*\/\s*\d{2}:\d{2}.*?(?=\s*(월|화|수|목|금|토|일|매일|$))/g);

        return (
            <ul className="list-disc pl-5 space-y-2">
                {timeItems?.map((item, index) => {
                    const [day, hours] = item.split(' / ');
                    return (
                        <li key={index} className="flex text-lg">
                            <strong className="text-gray-800">{day.trim()}</strong> / {hours ? hours.trim() : '운영시간 정보 없음'}
                        </li>
                    );
                })}
            </ul>
        );
    };

    return (
        <div className="bg-gray-100 mt-20">
            <div className="container mx-auto px-4 py-4 bg-white shadow-lg rounded-lg">
                {filteredRestaurants.length > 0 ? (
                    <ul className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-6">
                        {filteredRestaurants.map((restaurant) => (
                            <li key={restaurant.id} className="bg-white shadow-md rounded-lg overflow-hidden hover:shadow-xl transition-shadow duration-300">
                                <img
                                    src={restaurant.thumbnailImageUrl || '/default-thumbnail.jpg'}
                                    alt={restaurant.name}
                                    className="w-full h-64 object-cover"
                                />
                                <div className="p-4">
                                    <h2 className="text-xl font-bold mb-2">{restaurant.name}</h2>
                                    <p className="text-gray-600">유형: {restaurant.type}</p>
                                    <p className="text-gray-600">주소: {restaurant.address}</p>
                                    <p className="text-gray-600">전화번호: {restaurant.tel}</p>
                                    <p className="text-gray-600">평점: ★ {restaurant.rate}</p>
                                </div>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <>
                        <h1 className="text-4xl font-extrabold text-center text-gray-800 mb-4 shadow-md p-4 rounded-lg bg-gray-100">
                            {restaurant.name}
                        </h1>
                        <div className="flex mb-4">
                            <div className="w-2/3 pr-4">

                                <div className="flex mb-4">
                                    <img
                                        src={restaurant.thumbnailImageUrl || '/default-thumbnail.jpg'}
                                        alt={restaurant.name}
                                        style={{ width: '100%', height: '300px', objectFit: 'cover' }}


                                    />
                                    {restaurant.subImageUrl && (
                                        <img
                                            src={restaurant.subImageUrl || '/default-subimage.jpg'}
                                            alt={restaurant.name}
                                            style={{ width: '100%', height: '300px', objectFit: 'cover' }}

                                        />
                                    )}
                                </div>
                                <div className="text-gray-700 text-lg"><strong>유형:</strong> {restaurant.type}</div>
                                <div className="text-gray-700 text-lg"><strong>주소:</strong> {restaurant.address}</div>
                                <div className="text-gray-700 text-lg"><strong>전화번호:</strong> {restaurant.tel}</div>
                                <div className="text-gray-700 text-lg"><strong>[네이버 평점]</strong></div>
                                <div className="flex items-center">
                                    {restaurant.rate != null && restaurant.rate !== 0 ? (
                                        <div className="flex items-center">
                                            <Star w="w-6" h="h-6" readonly={true} rate={restaurant.rate} onChange={() => { }} />
                                            <p className="ml-2">{restaurant.rate.toFixed(1)} / 5</p>
                                        </div>
                                    ) : '등록된 평점이 없습니다'}
                                </div>
                                <div className="mb-4">
                                    <strong className="text-lg">[레스토랑 전체 평점]</strong>
                                    {allAverage !== null ? (
                                        <div className="flex items-center">
                                            <Star w="w-6" h="h-6" readonly={true} rate={allAverage} onChange={() => { }} />
                                            <p className="ml-2">{allAverage.toFixed(1)} / 5</p>
                                        </div>
                                    ) : '등록된 평점이 없습니다.'}
                                </div>
                                <strong className="text-lg">메뉴</strong>
                                <ul role="list" className="marker:text-sky-400 list-disc pl-5 space-y-3 text-slate-500">
                                    <div className="whitespace-pre-line">
                                        {renderMenu(restaurant.menu)}
                                    </div>
                                </ul>
                                <strong className="text-lg">운영시간</strong>
                                <div>{renderOperTime(restaurant.operation)}</div>
                            </div>
                            <div className="w-1/3 h-80 rounded-lg shadow-md mt-0">
                                <div id="map" className="w-full h-full rounded-lg shadow-md mb-4" ></div>

                                {/* 광고 이미지 추가 */}
                                <div className="w-1/3 mx-auto mb-4">
                                    <img
                                        src="https://s0.2mdn.net/simgad/3899732977164778681"
                                        alt="광고 이미지"
                                        className="w-full h-100 object-cover"
                                    />
                                </div>
                            </div>
                        </div>


                    </>
                )}

                <div className="mb-4">
                    <h2 className="text-lg font-bold mb-2">[가장 많이 선택된 태그]</h2>
                    {tags.length > 0 ? (
                        <ul className="flex flex-wrap gap-2">
                            {tags.map((tag, index) => (
                                <li
                                    key={index}
                                    className="rounded-full border border-sky-100 bg-sky-50 px-2 py-1 text-sky-700"
                                >
                                    {tag}
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>등록된 태그가 없습니다.</p>
                    )}
                </div>
                <div className="text-center">
                    <button className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded transition duration-300"
                            onClick={() => router.push(`/post/${id}`)}>
                        후기 보기
                    </button>
                </div>
            </div>
        </div>
    );
};

