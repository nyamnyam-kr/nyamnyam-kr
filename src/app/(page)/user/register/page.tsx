"use client";
import React, {useState} from 'react';
import {useRouter} from 'next/navigation';
import Link from "next/link"; // 사용자 등록 API 호출 함수
import * as Icon from "@phosphor-icons/react/dist/ssr";
import {addUser} from "src/app/service/user/user.api";


export default function Register() {
    const router = useRouter();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [nickname, setNickname] = useState('');
    const [name, setName] = useState('');
    const [age, setAge] = useState<number | string>(''); // 초기값은 빈 문자열로 설정
    const [tel, setTel] = useState('');
    const [gender, setGender] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    // 회원가입 처리 함수
    const handleRegister = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const user = {
                username,
                password,
                nickname,
                name,
                age: typeof age === 'string' ? parseInt(age) : age, // 문자열을 숫자로 변환
                tel,
                gender,
                enabled: true, // 기본값으로 true 설정
                role: 'user', // 기본값으로 'user' 역할 설정
            };

           // const newUser = await addUser(user); // 사용자 등록 API 호출
           // console.log('User registered:', newUser);
            // 회원가입 후 로그인 페이지로 리디렉션
            router.push("/login");
        } catch (error) {
            console.error('Registration failed:', error);
            setErrorMessage('Registration failed. Please try again.');
        }
    };

    return (
        <div className="register-block md:py-20 py-10 mt-10"  style={{ borderRadius: '20px', overflow: 'hidden', backgroundColor: '#f9f9f9' }}>
            <div className="container">
                <div className="content-main flex gap-y-8 max-md:flex-col p-5">
                    <div className="left md:w-1/2 w-full lg:pr-[60px] md:pr-[40px] md:border-r border-line">
                        <div className="heading4">Register</div>
                        <form className="md:mt-7 mt-4">
                            <div className="email ">
                                <input className="border-line px-4 pt-3 pb-3 w-full rounded-lg" id="username"
                                       type="email" placeholder="Username or email address *" required/>
                            </div>
                            <div className="pass mt-5">
                                <input className="border-line px-4 pt-3 pb-3 w-full rounded-lg" id="password"
                                       type="password" placeholder="Password *" required/>
                            </div>
                            <div className="confirm-pass mt-5">
                                <input className="border-line px-4 pt-3 pb-3 w-full rounded-lg" id="confirmPassword"
                                       type="password" placeholder="Confirm Password *" required/>
                            </div>
                            <div className='flex items-center mt-5'>
                                <div className="block-input">
                                    <input
                                        type="checkbox"
                                        name='remember'
                                        id='remember'
                                    />
                                    <Icon.CheckSquare size={20} weight='fill' className='icon-checkbox'/>
                                </div>
                                <label htmlFor='remember' className="pl-2 cursor-pointer text-secondary2">I agree to the
                                    <Link href={'#!'} className='text-black hover:underline pl-1'>Terms of User</Link>
                                </label>
                            </div>
                            <div className="block-button md:mt-7 mt-4">
                                <button className="button-main">Register</button>
                            </div>
                        </form>
                    </div>
                    <div className="right md:w-1/2 w-full lg:pl-[60px] md:pl-[40px] flex items-center">
                        <div className="text-content">
                            <div className="heading4">Already have an account?</div>
                            <div className="mt-2 text-secondary">Welcome back. Sign in to access your personalized
                                experience, saved preferences, and more. We{String.raw`'re`} thrilled to have you with
                                us again!
                            </div>
                            <div className="block-button md:mt-7 mt-4">
                                <Link href={'/login'} className="button-main">Login</Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    );
}


// <div className="flex items-center justify-center h-screen">
//     <div className="w-full max-w-xs">
//         <form onSubmit={handleRegister} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
//             <h2 className="text-center text-2xl mb-4">Register</h2>
//             {errorMessage && <p className="text-red-500">{errorMessage}</p>}
//             <div className="mb-4">
//                 <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="username">
//                     Username
//                 </label>
//                 <input
//                     id="username"
//                     type="text"
//                     placeholder="Enter your username"
//                     value={username}
//                     onChange={(e) => setUsername(e.target.value)}
//                     className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//                 />
//             </div>
//             <div className="mb-4">
//                 <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="password">
//                     Password
//                 </label>
//                 <input
//                     id="password"
//                     type="password"
//                     placeholder="Enter your password"
//                     value={password}
//                     onChange={(e) => setPassword(e.target.value)}
//                     className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
//                 />
//             </div>
//             <div className="mb-4">
//                 <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="nickname">
//                     Nickname
//                 </label>
//                 <input
//                     id="nickname"
//                     type="text"
//                     placeholder="Enter your nickname"
//                     value={nickname}
//                     onChange={(e) => setNickname(e.target.value)}
//                     className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
//                 />
//             </div>
//             <div className="mb-4">
//                 <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="name">
//                     Name
//                 </label>
//                 <input
//                     id="name"
//                     type="text"
//                     placeholder="Enter your name"
//                     value={name}
//                     onChange={(e) => setName(e.target.value)}
//                     className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
//                 />
//             </div>
//             <div className="mb-4">
//                 <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="age">
//                     Age
//                 </label>
//                 <input
//                     id="age"
//                     type="number"
//                     placeholder="Enter your age"
//                     value={age}
//                     onChange={(e) => setAge(e.target.value)}
//                     className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
//                 />
//             </div>
//             <div className="mb-4">
//                 <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="tel">
//                     Telephone
//                 </label>
//                 <input
//                     id="tel"
//                     type="text"
//                     placeholder="Enter your telephone number"
//                     value={tel}
//                     onChange={(e) => setTel(e.target.value)}
//                     className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
//                 />
//             </div>
//             <div className="mb-4">
//                 <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="gender">
//                     Gender
//                 </label>
//                 <input
//                     id="gender"
//                     type="text"
//                     placeholder="Enter your gender"
//                     value={gender}
//                     onChange={(e) => setGender(e.target.value)}
//                     className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
//                 />
//             </div>
//             <div className="flex items-center justify-between">
//                 <button
//                     type="submit"
//                     className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
//                 >
//                     Register
//                 </button>
//             </div>
//         </form>
//     </div>
// </div>
