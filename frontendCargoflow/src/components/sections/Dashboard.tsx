import Icon from '../ui/Icon';

const KPI_CARDS = [
  { label: 'Incarichi oggi', cls: 'border-t-[#F97316]' },
  { label: 'In transito',    cls: 'border-t-[#F97316]' },
  { label: 'Consegnati oggi', cls: 'border-t-[#22C55E]' },
  { label: 'Anomalie aperte', cls: 'border-t-[#EF4444]' },
];

const PANELS = [
  { title: 'Alert attivi',       dot: '#EF4444', icon: 'alert',    desc: 'Nessun alert al momento.\nGli avvisi predittivi su ritardi e anomalie appariranno qui.' },
  { title: 'Incarichi recenti',  dot: '#F97316', icon: 'incarico', desc: 'Nessun incarico recente.\nGli ultimi incarichi inseriti appariranno qui.' },
  { title: 'SLA clienti',        dot: '#22C55E', icon: 'clienti',  desc: 'Score SLA per cliente.\nDisponibile quando saranno presenti dati storici.' },
  { title: 'Borderò del giorno', dot: '#F59E0B', icon: 'bordero',  desc: 'Nessun borderò attivo.\nI borderò assegnati per oggi appariranno qui.' },
];

export default function Dashboard() {
  return (
    <div>
      {/* KPI row */}
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-3.5 mb-6">
        {KPI_CARDS.map((k, i) => (
          <div key={i} className={`bg-[#112E4A] border border-[#0F3D5C] border-t-2 ${k.cls} rounded-xl p-5`}>
            <p className="font-mono text-[11px] uppercase tracking-wider text-[#7FA8C4] mb-3">{k.label}</p>
            <p className="font-mono text-3xl font-medium text-[#F1F5F9] leading-none">—</p>
            <p className="text-[11px] text-[#94A3B8] mt-2 italic">In attesa di dati</p>
          </div>
        ))}
      </div>

      {/* panels 2×2 */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {PANELS.map((p, i) => (
          <div key={i} className="bg-[#112E4A] border border-[#0F3D5C] rounded-xl p-5">
            <div className="flex items-center gap-2 mb-4">
              <span className="w-1.5 h-1.5 rounded-full" style={{ background: p.dot }} />
              <p className="font-mono text-[11px] uppercase tracking-wider text-[#7FA8C4]">{p.title}</p>
            </div>
            <div className="flex flex-col items-center justify-center py-8 text-[#7FA8C4] gap-3 text-center">
              <span className="opacity-30"><Icon name={p.icon} size={36} /></span>
              <p className="text-sm leading-relaxed whitespace-pre-line">{p.desc}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
