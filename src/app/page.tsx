"use client"
import Home from 'src/app/(page)/restaurant/page';
import './globals.css'
import StoreProvider from './StoreProvider';


const Page = () => {
    // const { setSearchTerm } = useSearchContext();

    // useEffect(() => {
    //     const params = new URLSearchParams(window.location.search);
    //     const term = params.get('search');
    //     if (term) {
    //         setSearchTerm(term);
    //     }
    // }, [setSearchTerm]);


    
    return (
        <StoreProvider>
            <Home />
        </StoreProvider>
    );
};

export default Page;