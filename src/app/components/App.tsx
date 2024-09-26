import StoreProvider from 'src/app/StoreProvider'; // StoreProvider 경로
import ShowNotice from "src/app/(page)/notice/page"; // 다른 컴포넌트

const Page = () => {
    return (
        <StoreProvider>
            <ShowNotice />
        </StoreProvider>
    );
};

export default Page;
