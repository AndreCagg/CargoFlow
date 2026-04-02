import { useState } from 'react';
import './app/globals.css';
import type { PageId } from './lib/constants';
import { NAV_MODULES } from './lib/constants';
import Sidebar from './components/sections/Sidebar';
import Dashboard from './components/sections/Dashboard';
import FormIncarico from './components/sections/FormIncarico';
import Toast from './components/ui/Toast';
import Icon from './components/ui/Icon';

function Placeholder({ title }: { title: string }) {
  return (
    <div className="flex flex-col items-center justify-center h-64 gap-4 text-[#7FA8C4]">
      <span className="opacity-30"><Icon name="pkg" size={40} /></span>
      <p className="text-sm"><strong className="text-[#F1F5F9]">{title}</strong> — modulo in sviluppo.</p>
    </div>
  );
}

export default function App() {
  const [page, setPage] = useState<PageId>('dashboard');
  const cur = NAV_MODULES.find((m) => m.id === page);

  return (
    <div className="flex h-screen overflow-hidden">
      <Sidebar page={page} onNavigate={setPage} />

      <div className="flex-1 flex flex-col overflow-hidden">
        {/* topbar */}
        <div className="px-7 py-3.5 border-b border-[#0F3D5C] bg-[#07213A] flex items-center justify-between sticky top-0 z-10">
          <div>
            <p className="font-mono text-[11px] text-[#7FA8C4] uppercase tracking-wider">CargoFlow</p>
            <p className="font-mono text-base font-medium text-[#F1F5F9] tracking-tight">{cur?.label}</p>
          </div>
          <div className="flex gap-2">
            {page === 'dashboard' && (
              <button
                onClick={() => setPage('incarico')}
                className="flex items-center gap-1.5 px-3.5 py-2 rounded-lg bg-[#F97316] hover:bg-[#EA6C0A] text-white text-xs font-medium transition-all shadow-lg hover:shadow-[0_0_16px_rgba(249,115,22,.3)]"
              >
                <Icon name="plus" size={13} />Nuovo incarico
              </button>
            )}
            {page !== 'dashboard' && (
              <button
                onClick={() => setPage('dashboard')}
                className="flex items-center gap-1.5 px-3.5 py-2 rounded-lg border border-[#1A5C8A] text-[#94A3B8] text-xs hover:border-[#F97316] hover:text-white transition-colors"
              >
                <Icon name="dashboard" size={13} />Dashboard
              </button>
            )}
          </div>
        </div>

        {/* content */}
        <div className="flex-1 overflow-y-auto p-7">
          {page === 'dashboard' && <Dashboard />}
          {page === 'incarico' && <FormIncarico />}
          {page === 'bordero' && <Placeholder title="Gestione Borderò" />}
          {page === 'clienti' && <Placeholder title="Anagrafica Clienti" />}
          {page === 'veicoli' && <Placeholder title="Gestione Veicoli" />}
        </div>
      </div>

      <Toast />
    </div>
  );
}
