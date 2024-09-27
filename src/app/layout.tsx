
import React from "react";
import Header from "src/app/components/common/Header";

export default function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="en">
      <head>
        <title>NyamNyam</title>
      </head>

      <body>
        <Header />
        <main>{children}</main>
      </body>
    </html>
  )
}