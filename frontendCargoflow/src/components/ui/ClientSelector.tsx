import { useState } from 'react';
import type { Client } from '../../lib/types';
import { MOCK_CLIENTS } from '../../lib/constants';
import Icon from './Icon';

// ─── Tipi ────────────────────────────────────────────────────────────────────

interface ClientSelectorProps {
  label: string;
  required?: boolean;
  value: string | Partial<Client> | null;
  onChange: (v: string | Partial<Client> | null) => void;
}

const emptyNew = (): Partial<Client> => ({
  ragione_sociale: '', indirizzo: '', cap: '', tel: '', email: '',
  vettore: false, fasce_orarie_consegna: [], fasce_orarie_ritiro: [],
});

// ─── Componente ──────────────────────────────────────────────────────────────
// ClientSelector è usato tramite <Controller> in FormIncarico, quindi rimane
// un componente "controllato" con value/onChange come props — non ha bisogno
// di useFormContext internamente. L'unica differenza rispetto all'originale
// è che le fasce orarie usano useFieldArray-like locale con stato semplice.

export default function ClientSelector({ label, required, value, onChange }: ClientSelectorProps) {
  const [mode, setMode]           = useState<'search' | 'new'>('search');
  const [q, setQ]                 = useState('');
  const [dropOpen, setDropOpen]   = useState(false);
  const [newClient, setNewClient] = useState<Partial<Client>>(emptyNew());

  const filtered = MOCK_CLIENTS.filter((c) =>
    c.ragione_sociale.toLowerCase().includes(q.toLowerCase())
  );

  const selectedObj =
    typeof value === 'string'
      ? (MOCK_CLIENTS.find((c) => c.id === value) ?? null)
      : null;

  const select = (c: Client) => { onChange(c.id); setQ(c.ragione_sociale); setDropOpen(false); };
  const clear  = () => { onChange(null); setQ(''); };

  // helper: aggiorna newClient e propaga onChange
  const patchNew = (patch: Partial<Client>) => {
    const updated = { ...newClient, ...patch };
    setNewClient(updated);
    onChange(updated);
  };

  const setField =
    (k: keyof Client) => (e: React.ChangeEvent<HTMLInputElement>) =>
      patchNew({ [k]: e.target.type === 'checkbox' ? e.target.checked : e.target.value });

  // ── fasce consegna ──────────────────────────────────────────────────────────
  const addFasciaConsegna = () =>
    patchNew({ fasce_orarie_consegna: [...(newClient.fasce_orarie_consegna ?? []), { giorno: '', dalle: '', alle: '' }] });

  const delFasciaConsegna = (i: number) =>
    patchNew({ fasce_orarie_consegna: newClient.fasce_orarie_consegna?.filter((_, j) => j !== i) });

  const setFasciaConsegna = (i: number, k: 'dalle' | 'alle' | 'giorno', v: string) =>
    patchNew({
      fasce_orarie_consegna: newClient.fasce_orarie_consegna?.map((f, j) =>
        j === i ? { ...f, [k]: v } : f
      ),
    });

  // ── fasce ritiro ────────────────────────────────────────────────────────────
  const addFasciaRitiro = () =>
    patchNew({ fasce_orarie_ritiro: [...(newClient.fasce_orarie_ritiro ?? []), { giorno: '', dalle: '', alle: '' }] });

  const delFasciaRitiro = (i: number) =>
    patchNew({ fasce_orarie_ritiro: newClient.fasce_orarie_ritiro?.filter((_, j) => j !== i) });

  const setFasciaRitiro = (i: number, k: 'dalle' | 'alle' | 'giorno', v: string) =>
    patchNew({
      fasce_orarie_ritiro: newClient.fasce_orarie_ritiro?.map((f, j) =>
        j === i ? { ...f, [k]: v } : f
      ),
    });

  const switchMode = (m: 'search' | 'new') => {
    setMode(m); onChange(null); setQ(''); setNewClient(emptyNew());
  };

  // ─── JSX ───────────────────────────────────────────────────────────────────

  return (
    <div>
      <label className="block mb-2 font-mono text-[11px] uppercase tracking-wider text-[#7FA8C4]">
        {label}{required && <span className="text-[#F97316] ml-0.5">*</span>}
      </label>

      {/* mode tabs */}
      <div className="flex bg-[#0A2840] border border-[#0F3D5C] rounded-lg p-0.5 mb-3.5">
        {(['search', 'new'] as const).map((m) => (
          <button
            key={m}
            type="button"
            onClick={() => switchMode(m)}
            className={`flex-1 py-1.5 rounded-md text-xs font-mono transition-all ${
              mode === m ? 'bg-[#F97316] text-white font-medium' : 'text-[#94A3B8] hover:text-white'
            }`}
          >
            {m === 'search' ? 'Cerca esistente' : 'Nuovo cliente'}
          </button>
        ))}
      </div>

      {/* search mode */}
      {mode === 'search' && (
        selectedObj ? (
          <div className="flex items-center justify-between bg-[#F97316]/10 border border-[#F97316]/30 rounded-lg px-3.5 py-2.5">
            <div>
              <strong className="text-[#F97316] text-sm">{selectedObj.ragione_sociale}</strong>
              <span className="block text-[11px] text-[#7FA8C4] mt-0.5">{selectedObj.indirizzo}</span>
            </div>
            <button type="button" onClick={clear} className="text-[#7FA8C4] text-lg leading-none ml-3">×</button>
          </div>
        ) : (
          <div className="relative">
            <input
              className="w-full bg-[#0A2840] border border-[#1A5C8A] rounded-lg px-3 py-2.5 pr-9 text-sm text-[#F1F5F9] placeholder:text-[#7FA8C4]/50 outline-none focus:border-[#F97316] transition-colors"
              placeholder="Cerca per ragione sociale…"
              value={q}
              onChange={(e) => { setQ(e.target.value); setDropOpen(true); }}
              onFocus={() => setDropOpen(true)}
              onBlur={() => setTimeout(() => setDropOpen(false), 150)}
            />
            <span className="absolute right-3 top-1/2 -translate-y-1/2 text-[#7FA8C4]">
              <Icon name="search" size={14} />
            </span>
            {dropOpen && q && filtered.length > 0 && (
              <div className="absolute z-20 w-full mt-1 bg-[#0A2840] border border-[#1A5C8A] rounded-lg overflow-hidden shadow-xl">
                {filtered.map((c) => (
                  <div
                    key={c.id}
                    onMouseDown={() => select(c)}
                    className="px-3.5 py-2.5 cursor-pointer hover:bg-[#F97316]/8 border-b border-[#0F3D5C] last:border-0 transition-colors"
                  >
                    <strong className="block text-sm text-[#F1F5F9]">{c.ragione_sociale}</strong>
                    <span className="text-[11px] text-[#7FA8C4]">
                      {c.indirizzo}
                      {c.vettore && (
                        <span className="ml-2 px-1.5 py-0.5 rounded-full bg-[#F97316]/15 text-[#F97316] text-[10px] border border-[#F97316]/25">
                          vettore
                        </span>
                      )}
                    </span>
                  </div>
                ))}
              </div>
            )}
          </div>
        )
      )}

      {/* new client mode */}
      {mode === 'new' && (
        <div className="space-y-3">
          <div className="grid grid-cols-2 gap-3">
            <Field label="Ragione sociale" required>
              <input className="cf-inp" value={newClient.ragione_sociale ?? ''} onChange={setField('ragione_sociale')} />
            </Field>
            <Field label="Indirizzo">
              <input className="cf-inp" value={newClient.indirizzo ?? ''} onChange={setField('indirizzo')} />
            </Field>
          </div>
          <div className="grid grid-cols-3 gap-3">
            <Field label="CAP">
              <input className="cf-inp" value={newClient.cap ?? ''} onChange={setField('cap')} />
            </Field>
            <Field label="Tel">
              <input className="cf-inp" value={newClient.tel ?? ''} onChange={setField('tel')} />
            </Field>
            <Field label="Email">
              <input className="cf-inp" type="email" value={newClient.email ?? ''} onChange={setField('email')} />
            </Field>
          </div>

          {/* fasce consegna */}
          <FasceSection
            title="Fasce orarie consegna"
            items={newClient.fasce_orarie_consegna ?? []}
            onAdd={addFasciaConsegna}
            onDelete={delFasciaConsegna}
            onChange={setFasciaConsegna}
          />

          {/* fasce ritiro */}
          <FasceSection
            title="Fasce orarie ritiro"
            items={newClient.fasce_orarie_ritiro ?? []}
            onAdd={addFasciaRitiro}
            onDelete={delFasciaRitiro}
            onChange={setFasciaRitiro}
          />
        </div>
      )}
    </div>
  );
}

// ─── FasceSection (estratto per evitare duplicazione) ─────────────────────────

function FasceSection({
  title, items, onAdd, onDelete, onChange,
}: {
  title: string;
  items: { giorno: string; dalle: string; alle: string }[];
  onAdd: () => void;
  onDelete: (i: number) => void;
  onChange: (i: number, k: 'dalle' | 'alle' | 'giorno', v: string) => void;
}) {
  return (
    <div>
      <p className="font-mono text-[11px] uppercase tracking-wider text-[#7FA8C4] mb-2">
        {title} <span className="opacity-50 normal-case">(opz.)</span>
      </p>
      <div className="space-y-2 mb-2">
        {items.map((f, i) => (
          <div key={i} className="flex items-center gap-2">
            <input
              type="text"
              className="cf-inp flex-1"
              value={f.giorno}
              onChange={(e) => onChange(i, 'giorno', e.target.value)}
            />
            <span className="text-[#7FA8C4] text-xs shrink-0">—</span>
            <input
              type="time"
              className="cf-inp flex-1"
              value={f.dalle}
              onChange={(e) => onChange(i, 'dalle', e.target.value)}
            />
            <span className="text-[#7FA8C4] text-xs shrink-0">—</span>
            <input
              type="time"
              className="cf-inp flex-1"
              value={f.alle}
              onChange={(e) => onChange(i, 'alle', e.target.value)}
            />
            <button
              type="button"
              onClick={() => onDelete(i)}
              className="w-7 h-7 flex items-center justify-center rounded bg-red-500/10 border border-red-500/20 text-red-400 hover:bg-red-500/20 transition-colors shrink-0"
            >
              <Icon name="trash" size={12} />
            </button>
          </div>
        ))}
      </div>
      <button
        type="button"
        onClick={onAdd}
        className="text-xs px-3 py-1.5 rounded-lg border border-[#1A5C8A] text-[#94A3B8] hover:border-[#F97316] hover:text-white transition-colors flex items-center gap-1.5"
      >
        <Icon name="plus" size={12} />Aggiungi fascia
      </button>
    </div>
  );
}

// ─── Field helper ─────────────────────────────────────────────────────────────

function Field({
  label, required, children,
}: {
  label: string; required?: boolean; children: React.ReactNode;
}) {
  return (
    <div className="flex flex-col gap-1.5">
      <label className="font-mono text-[11px] uppercase tracking-wider text-[#7FA8C4]">
        {label}{required && <span className="text-[#F97316] ml-0.5">*</span>}
      </label>
      {children}
    </div>
  );
}