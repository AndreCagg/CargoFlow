import { useState, useEffect } from 'react';
import { useFormContext, useWatch, Controller } from 'react-hook-form';
import type { MerceItem as MerceItemType } from '../../lib/types';
import { MOCK_ADR } from '../../lib/constants';
import Icon from './Icon';
import { MerceTipo } from '../../lib/types';
import { getTipiMerce } from '../../util/api';

// ─── Tipi ────────────────────────────────────────────────────────────────────

interface MerceItemProps {
  idx: number;
  /**
   * Valore corrente dell'item (passato da FormIncarico via watch).
   * Serve solo per visualizzare l'header dell'accordion in modo reattivo.
   */
  item: MerceItemType;
  onChange: (v: MerceItemType) => void;
  onDelete: () => void;
}

// ─── Componente ──────────────────────────────────────────────────────────────

export default function MerceItemComponent({ idx, item, onChange, onDelete }: MerceItemProps) {
  const [open, setOpen] = useState(true);
  const [adrQ, setAdrQ] = useState('');
  const [adrDrop, setAdrDrop] = useState(false);
  const [MERCE_TIPI, setMerceTipi] = useState<MerceTipo[]>([]);

  const { register, control, setValue, getValues } = useFormContext();
  const prefix = `merce.${idx}` as const;

  // watch selettivo per i campi che influenzano il render
  const hasAdr = useWatch({ control, name: `${prefix}._hasAdr` });
  const epal    = useWatch({ control, name: `${prefix}.epal` });
  const adrId   = useWatch({ control, name: `${prefix}.adr.id_adr_elemento` });

  useEffect(() => {
    getTipiMerce().then((res) => setMerceTipi(res.data));
  }, []);

  const filteredAdr = MOCK_ADR.filter((a) =>
    a.label.toLowerCase().includes(adrQ.toLowerCase())
  );
  const selectedAdr = MOCK_ADR.find((a) => a.id === Number(adrId));
  const tipoLabel   = MERCE_TIPI.find((t) => t.id === Number(item.merce_tipo))?.descrizione;

  // helper per aggiornare un campo e propagare onChange al parent
  const patch = (k: string, v: unknown) => {
    setValue(`${prefix}.${k}`, v, { shouldDirty: true });
    onChange({ ...getValues(prefix) });
  };

  return (
    <div className="bg-[#0A2840] border border-[#1A5C8A] rounded-xl overflow-hidden">
      {/* header */}
      <div
        className="flex items-center justify-between px-4 py-3 cursor-pointer"
        onClick={() => setOpen(!open)}
      >
        <div className="flex items-center gap-2.5">
          <span className="w-6 h-6 rounded bg-[#F97316]/15 border border-[#F97316]/30 flex items-center justify-center font-mono text-[11px] text-[#F97316]">
            {idx + 1}
          </span>
          <div>
            <div className="font-mono text-sm text-[#F1F5F9]">
              {item.segnacollo || `Collo ${idx + 1}`}
            </div>
            <div className="text-[11px] text-[#7FA8C4] mt-0.5 flex items-center gap-2">
              {tipoLabel ?? '—'}
              {item._hasAdr && (
                <span className="px-1.5 py-0.5 rounded-full bg-red-500/12 text-red-400 border border-red-500/20 text-[10px]">
                  ADR
                </span>
              )}
            </div>
          </div>
        </div>
        <div className="flex items-center gap-2">
          <button
            type="button"
            onClick={(e) => { e.stopPropagation(); onDelete(); }}
            className="px-2 py-1 rounded bg-red-500/10 border border-red-500/15 text-red-400 text-[11px] hover:bg-red-500/20 transition-colors flex items-center gap-1"
          >
            <Icon name="trash" size={11} />
          </button>
          <span className={`text-[#7FA8C4] transition-transform duration-200 ${open ? 'rotate-180' : ''}`}>
            <Icon name="chevron" size={15} />
          </span>
        </div>
      </div>

      {open && (
        <div className="px-4 pb-4 pt-3.5 border-t border-[#0F3D5C] space-y-3.5">
          {/* segnacollo + tipo */}
          <div className="grid grid-cols-2 gap-3">
            <CF label="Segnacollo" required>
              <input
                className="cf-inp"
                {...register(`${prefix}.segnacollo`, { required: true })}
              />
            </CF>
            <CF label="Tipo merce" required>
              <select
                className="cf-inp"
                {...register(`${prefix}.merce_tipo`, { required: true })}
              >
                <option value="">Seleziona…</option>
                {MERCE_TIPI.map((t) => (
                  <option key={t.id} value={t.id}>{t.descrizione}</option>
                ))}
              </select>
            </CF>
          </div>

          {/* peso + volume */}
          <div className="grid grid-cols-2 gap-3">
            <CF label="Peso (kg)" opt>
              <input className="cf-inp" type="number" step="0.1" {...register(`${prefix}.peso_kg`)} />
            </CF>
            <CF label="Volume (m³)" opt>
              <input className="cf-inp" type="number" step="0.01" {...register(`${prefix}.volume_m3`)} />
            </CF>
          </div>

          {/* flags */}
          <div className="flex flex-wrap gap-5">
            <Chk id={`fragile-${idx}`} label="Fragile" {...register(`${prefix}.fragile`)} />
            <Chk id={`epal-${idx}`}    label="Epal"    {...register(`${prefix}.epal`)} />
            {epal && (
              <CF label="ID Epal" opt>
                <input className="cf-inp" {...register(`${prefix}.id_epal`)} />
              </CF>
            )}
          </div>

          {/* ADR toggle */}
          <div className="pt-3 border-t border-[#0F3D5C]">
            <Controller
              name={`${prefix}._hasAdr`}
              control={control}
              render={({ field }) => (
                <Chk
                  id={`adr-${idx}`}
                  label="Merci ADR"
                  checked={!!field.value}
                  onChange={(e) => {
                    const v = e.target.checked;
                    field.onChange(v);
                    if (v) {
                      setValue(`${prefix}.adr`, {
                        codice_imballaggio: '', qta: '', um: 'L', id_adr_elemento: '',
                      });
                    } else {
                      setValue(`${prefix}.adr`, undefined);
                    }
                  }}
                />
              )}
            />
          </div>

          {hasAdr && (
            <div className="bg-[#F97316]/4 border border-[#F97316]/15 rounded-lg p-3.5 space-y-3">
              <p className="font-mono text-[11px] uppercase tracking-wider text-[#F97316]">Dati ADR</p>

              <div className="grid grid-cols-2 gap-3">
                {/* ricerca elemento ADR */}
                <CF label="Elemento ADR" required>
                  {selectedAdr ? (
                    <div className="flex items-center justify-between bg-[#F97316]/10 border border-[#F97316]/30 rounded-lg px-3 py-2">
                      <span className="text-xs text-[#F97316]">{selectedAdr.label}</span>
                      <button
                        type="button"
                        onClick={() => { patch('adr.id_adr_elemento', ''); setAdrQ(''); }}
                        className="text-[#7FA8C4] ml-2"
                      >
                        ×
                      </button>
                    </div>
                  ) : (
                    <div className="relative">
                      <input
                        className="cf-inp w-full"
                        placeholder="Cerca UN / classe…"
                        value={adrQ}
                        onChange={(e) => { setAdrQ(e.target.value); setAdrDrop(true); }}
                        onFocus={() => setAdrDrop(true)}
                        onBlur={() => setTimeout(() => setAdrDrop(false), 150)}
                      />
                      {adrDrop && adrQ && (
                        <div className="absolute z-20 w-full mt-1 bg-[#0A2840] border border-[#1A5C8A] rounded-lg overflow-hidden shadow-xl">
                          {filteredAdr.map((a) => (
                            <div
                              key={a.id}
                              onMouseDown={() => {
                                patch('adr.id_adr_elemento', a.id);
                                setAdrQ(a.label);
                                setAdrDrop(false);
                              }}
                              className="px-3 py-2 text-xs text-[#F1F5F9] cursor-pointer hover:bg-[#F97316]/8 border-b border-[#0F3D5C] last:border-0"
                            >
                              {a.label}
                            </div>
                          ))}
                        </div>
                      )}
                    </div>
                  )}
                </CF>

                <CF label="Codice imballaggio" required>
                  <input
                    className="cf-inp"
                    {...register(`${prefix}.adr.codice_imballaggio`, { required: hasAdr })}
                  />
                </CF>
              </div>

              <div className="grid grid-cols-2 gap-3">
                <CF label="Quantità" required>
                  <input
                    className="cf-inp"
                    type="number"
                    step="0.1"
                    {...register(`${prefix}.adr.qta`, { required: hasAdr })}
                  />
                </CF>
                <CF label="Unità di misura" required>
                  <select className="cf-inp" {...register(`${prefix}.adr.um`)}>
                    {['L', 'kg', 'm³', 'pz'].map((u) => <option key={u}>{u}</option>)}
                  </select>
                </CF>
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
}

// ─── Helper components ────────────────────────────────────────────────────────

function CF({
  label, required, opt, children,
}: {
  label: string; required?: boolean; opt?: boolean; children: React.ReactNode;
}) {
  return (
    <div className="flex flex-col gap-1.5">
      <label className="font-mono text-[11px] uppercase tracking-wider text-[#7FA8C4]">
        {label}
        {required && <span className="text-[#F97316] ml-0.5">*</span>}
        {opt && <span className="opacity-50 ml-1 normal-case">(opz.)</span>}
      </label>
      {children}
    </div>
  );
}

import { forwardRef } from 'react';

const Chk = forwardRef<
  HTMLInputElement,
  { id: string; label: string } & React.InputHTMLAttributes<HTMLInputElement>
>(({ id, label, ...rest }, ref) => (
  <label htmlFor={id} className="flex items-center gap-2 text-sm text-[#94A3B8] cursor-pointer py-1">
    <input id={id} type="checkbox" className="accent-[#F97316] w-4 h-4" ref={ref} {...rest} />
    {label}
  </label>
));
Chk.displayName = 'Chk';