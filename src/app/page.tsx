"use client"
import Home from 'src/app/(page)/restaurant/page';
import './globals.css'
import StoreProvider from './StoreProvider';
import { SearchProvider, useSearchContext } from './components/SearchContext';
import { useEffect, useState } from 'react';
import Header from './components/common/Header';
import TabFeatures from './(page)/restaurant/page2';
import { data } from 'jquery';
import { getRestaurantsByTag } from './service/restaurant/restaurant.service';



const Page = () => {
    const { searchTerm } = useSearchContext(); // Context에서 검색어 가져오기
    const [showHome, setShowHome] = useState(false);
 


    useEffect(() => {
        setShowHome(!!searchTerm); // 검색어가 있을 경우 Home 컴포넌트를 보여주는
    }, [searchTerm]);

    return (
        <StoreProvider>
            {showHome ? <Home /> : <TabFeatures start={0} limit={5}/>} 
        </StoreProvider>
    );
};

export default Page;