import StoreProvider from './StoreProvider';
import Restaurant from "src/app/(page)/restaurant/page";
import ShowNotice from "src/app/(page)/notice/page";

const Page = () => {
    return (
        <StoreProvider>
            <ShowNotice />
        </StoreProvider>
    );
};

export default Page;