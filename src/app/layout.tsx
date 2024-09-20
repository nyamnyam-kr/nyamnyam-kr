"use client";
import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Link from "next/link"; 

const inter = Inter({ subsets: ["latin"] });

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
       <head>
        <title> 냠냠! </title>
      </head>
      <body className={inter.className}>
      <nav className="bg-blue-500 text-white py-4">
        <div className="container mx-auto flex justify-between items-center px-4">
          <Link href="/" className="text-xl font-bold">냠냠</Link>
          <ul className="flex space-x-4">
            <li>
              <Link href="/" className="hover:bg-blue-600 px-3 py-2 rounded">홈</Link>
            </li>
            <li>
              <Link href="/" className="hover:bg-blue-600 px-3 py-2 rounded">POST</Link>
            </li>
            <li>
              <Link href="/reply/replies" className="hover:bg-blue-600 px-3 py-2 rounded">Reply</Link>
            </li>
            <li>
              <Link href="/tag/tags" className="hover:bg-blue-600 px-3 py-2 rounded">Tag</Link>
            </li>
          </ul>
        </div>
      </nav>
      <div className="container mx-auto p-4">
        {children}
      </div>
    </body>
    </html>
  );
}
