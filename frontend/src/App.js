import React from 'react';
import './App.css';
import AlarmDashboard from './components/AlarmDashboard';

function App() {
  return (
    <div className="App">
      <header className="bg-dark text-white p-3 mb-4 shadow text-center">
        <h1>Hikvision System</h1>
        <p>Vezérlőpult</p>
      </header>
      <main className="container">
        <AlarmDashboard/>
      </main>

      <footer className="bg-light text-center p-3 mt-4 shadow">
        <small>&copy; 2024 Gubola Ádám</small>
      </footer>
    </div>
  );
}

export default App;
