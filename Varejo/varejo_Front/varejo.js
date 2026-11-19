/* Centraliza listas de opções e regras para manter os formulários consistentes. */
const CONFIG = {
    storageKey: "rede-varejo-service-desk-v1",
    pageNames: {
        dashboard: "Visão geral",
        tickets: "Chamados",
        catalog: "Catálogo e modelos",
        knowledge: "Base de conhecimento",
        people: "Usuários e equipes",
        rules: "Prioridades e regras",
        reports: "Relatórios e testes"
    },
    types: ["Incidente", "Solicitação de serviço", "Mudança/melhoria"],
    categories: [
        "PDV",
        "Internet",
        "Impressão",
        "Estoque",
        "Usuários",
        "Equipamentos da loja",
        "Wi-Fi",
        "Integração com matriz"
    ],
    impactLevels: ["Baixo", "Médio", "Alto"],
    urgencyLevels: ["Baixa", "Média", "Alta"],
    supportLevels: ["N1", "N2", "N3"],
    businessHours: { days: [1, 2, 3, 4, 5, 6], start: 8, end: 22 },
    priorities: {
        "Crítica": { responseMinutes: 15, resolutionMinutes: 120, color: "critical" },
        "Alta": { responseMinutes: 60, resolutionMinutes: 480, color: "high" },
        "Média": { responseMinutes: 240, resolutionMinutes: 1440, color: "medium" },
        "Baixa": { responseMinutes: 480, resolutionMinutes: 4320, color: "low" }
    },
    statuses: [
        "Novo",
        "Em triagem",
        "Em atendimento",
        "Aguardando solicitante",
        "Suporte remoto",
        "Resolvido",
        "Encerrado"
    ],
    stores: [
        "Matriz",
        "São Paulo · Centro",
        "Rio de Janeiro · Copacabana",
        "Belo Horizonte · Savassi",
        "Curitiba · Batel"
    ]
};

const view = document.querySelector("#inicio");
const dialog = document.querySelector("#ticket-dialog");
const dialogContent = document.querySelector("#dialog-content");
const authDialog = document.querySelector("#auth-dialog");
const toast = document.querySelector("#toast");
const globalSearch = document.querySelector("#global-search");
const TICKET_IDLE_TIMEOUT_MS = 60_000;
let data;
let currentPage = "dashboard";
let ticketFilter = "Todos";
let dialogMode = "new";
let selectedTicketId = null;
let toastTimeout;
let ticketIdleTimeout;
let ticketIdleCountdown;
let ticketIdleDeadline;

/* Gera datas de demonstração relativas ao momento em que os dados são criados. */
function minutesAgo(minutes) {
    return new Date(Date.now() - minutes * 60_000).toISOString();
}

/* Cria lista inicial de usuários com e-mail e senha compatíveis com a tabela do banco de dados. */
function createUsers() {
    const requesters = [
        ["u-requester-1", "Operador Loja BH", "loja.bh@varejo.com", "123456", "Belo Horizonte · Savassi"],
        ["u-requester-2", "Ana Ribeiro", "ana.ribeiro@varejo.com", "123456", "Rio de Janeiro · Copacabana"],
        ["u-requester-3", "Bruno Lima", "bruno.lima@varejo.com", "123456", "Matriz"],
        ["u-requester-4", "Marina Costa", "marina.costa@varejo.com", "123456", "São Paulo · Centro"],
        ["u-requester-5", "Rafael Alves", "rafael.alves@varejo.com", "123456", "Rio de Janeiro · Copacabana"],
        ["u-requester-6", "Júlia Prado", "julia.prado@varejo.com", "123456", "Belo Horizonte · Savassi"],
        ["u-requester-7", "Lucas Ferreira", "lucas.ferreira@varejo.com", "123456", "Curitiba · Batel"],
        ["u-requester-8", "Camila Rocha", "camila.rocha@varejo.com", "123456", "Matriz"]
    ].map(([id, name, email, password, store]) => ({
        id, name, email, password, role: "Solicitante", level: null, store
    }));

    const support = [
        { id: "u-tech-1", name: "Diego Nunes", email: "diego.nunes@varejo.com", password: "123456", role: "Técnico", level: "N1", store: "Matriz" },
        { id: "u-tech-2", name: "Fernanda Melo", email: "fernanda.melo@varejo.com", password: "123456", role: "Técnico", level: "N1", store: "Matriz" },
        { id: "u-tech-3", name: "Caio Souza", email: "caio.souza@varejo.com", password: "123456", role: "Técnico", level: "N2", store: "Matriz" },
        { id: "u-supervisor-1", name: "Patrícia Gomes", email: "patricia.gomes@varejo.com", password: "123456", role: "Supervisor", level: "N3", store: "Matriz" }
    ];

    return [...requesters, ...support];
}

/* Cria um chamado de teste com o histórico inicial e prazos calculados. */
function seedTicket(ticket, index) {
    const createdAt = minutesAgo((index + 1) * 11);
    const sla = CONFIG.priorities[ticket.priority];
    const solution = ["Resolvido", "Encerrado"].includes(ticket.status)
        ? ticket.solution || "Atendimento concluído e validado com a unidade."
        : "";
    const history = [
        {
            at: createdAt,
            user: "Sistema",
            text: `Chamado registrado e encaminhado para ${ticket.level}.`
        }
    ];

    if (ticket.scenario?.includes("Escalonamento funcional")) {
        history.push({
            at: new Date(new Date(createdAt).getTime() + 60_000).toISOString(),
            user: "Diego Nunes · N1",
            text: "Verificações iniciais concluídas; escalonado de N1 para N2 por impacto no serviço da loja."
        });
    }
    if (ticket.scenario?.includes("Escalonamento técnico")) {
        history.push({
            at: new Date(new Date(createdAt).getTime() + 60_000).toISOString(),
            user: "Caio Souza · N2",
            text: "Falha exige análise especializada; escalonado de N2 para N3."
        });
    }
    if (ticket.remoteAuthorized) {
        history.push({
            at: new Date(new Date(createdAt).getTime() + 60_000).toISOString(),
            user: "Solicitante da unidade",
            text: "Autorização da unidade para suporte remoto confirmada antes do diagnóstico."
        });
        history.push({
            at: new Date(new Date(createdAt).getTime() + 60_000).toISOString(),
            user: getSeedSupportName(ticket.assigneeId),
            text: "Diagnóstico remoto simulado iniciado; a equipe registrou os testes realizados com a unidade."
        });
    }
    if (solution) {
        history.push({
            at: new Date(new Date(createdAt).getTime() + 60_000).toISOString(),
            user: getSeedSupportName(ticket.assigneeId),
            text: `Solução registrada antes da conclusão: ${solution}`
        });
        if (ticket.status === "Encerrado") {
            history.push({
                at: new Date(new Date(createdAt).getTime() + 60_000).toISOString(),
                user: "Sistema",
                text: "Chamado encerrado após registro da solução."
            });
        }
    }

    return {
        ...ticket,
        id: `VJ-${2500 + index}`,
        createdAt,
        firstResponseAt: ticket.status === "Novo" ? null : minutesAgo((index + 1) * 5),
        solution,
        history,
        responseDueAt: new Date(new Date(createdAt).getTime() + sla.responseMinutes * 60_000).toISOString(),
        resolutionDueAt: new Date(new Date(createdAt).getTime() + sla.resolutionMinutes * 60_000).toISOString()
    };
}

function getSeedSupportName(userId) {
    const names = {
        "u-tech-1": "Diego Nunes",
        "u-tech-2": "Fernanda Melo",
        "u-tech-3": "Caio Souza",
        "u-supervisor-1": "Patrícia Gomes"
    };
    return names[userId] || "Equipe de suporte";
}

/* Preenche o sistema com os usuários, regras, artigos e cenários. */
function createInitialData() {
    const users = createUsers();
    const requesterIds = users.filter((user) => user.role === "Solicitante").map((user) => user.id);
    const tickets = [
        {
            subject: "Dúvida sobre fechamento de caixa",
            description: "A unidade pediu orientação para conferir o resumo do fechamento do caixa.",
            type: "Incidente",
            category: "PDV",
            store: "São Paulo · Centro",
            requesterId: requesterIds[0],
            level: "N1",
            assigneeId: "u-tech-1",
            impact: "Baixo",
            urgency: "Baixa",
            priority: "Baixa",
            status: "Encerrado",
            scenario: "Dúvida simples · N1 e base de conhecimento",
            solution: "Orientação do artigo KB-001 aplicada; solicitante confirmou o fechamento."
        },
        {
            subject: "Redefinição de senha do sistema de estoque",
            description: "A pessoa solicitante não consegue entrar no sistema e precisa redefinir o acesso.",
            type: "Solicitação de serviço",
            category: "Usuários",
            store: "Rio de Janeiro · Copacabana",
            requesterId: requesterIds[1],
            level: "N1",
            assigneeId: "u-tech-2",
            impact: "Baixo",
            urgency: "Média",
            priority: "Baixa",
            status: "Em atendimento",
            scenario: "Redefinição/problema de acesso"
        },
        {
            subject: "Usuário não consegue acessar o PDV",
            description: "Uma pessoa da loja não consegue autenticar no caixa; os demais caixas seguem funcionando.",
            type: "Incidente",
            category: "PDV",
            store: "Belo Horizonte · Savassi",
            requesterId: requesterIds[2],
            level: "N1",
            assigneeId: "u-tech-1",
            impact: "Baixo",
            urgency: "Média",
            priority: "Baixa",
            status: "Novo",
            scenario: "Falha em um usuário · impacto baixo/médio"
        },
        {
            subject: "Falha no PDV de um setor da loja",
            description: "Os caixas da área de atendimento estão lentos e a fila começou a aumentar.",
            type: "Incidente",
            category: "PDV",
            store: "Curitiba · Batel",
            requesterId: requesterIds[3],
            level: "N2",
            assigneeId: "u-tech-3",
            impact: "Alto",
            urgency: "Média",
            priority: "Alta",
            status: "Em atendimento",
            scenario: "Falha em um setor · prioridade alta"
        },
        {
            subject: "Loja sem internet durante o horário comercial",
            description: "A conexão da loja caiu e impede o funcionamento dos serviços de venda conectados.",
            type: "Incidente",
            category: "Internet",
            store: "São Paulo · Centro",
            requesterId: requesterIds[4] || requesterIds[0],
            level: "N2",
            assigneeId: "u-tech-3",
            impact: "Alto",
            urgency: "Alta",
            priority: "Crítica",
            status: "Em triagem",
            scenario: "Serviço crítico indisponível · prazo crítico"
        }
    ].map(seedTicket);

    return {
        version: 1,
        nextTicketNumber: 2514,
        currentUserId: "u-requester-1", // Usuário logado por padrão na primeira execução
        users,
        stores: CONFIG.stores,
        categories: [...CONFIG.categories],
        teams: [
            { level: "N1", name: "Suporte inicial", purpose: "Dúvidas, acesso e diagnóstico básico", members: ["u-tech-1", "u-tech-2"] },
            { level: "N2", name: "Suporte especializado", purpose: "Infraestrutura, PDV e incidentes de alto impacto", members: ["u-tech-3"] },
            { level: "N3", name: "Especialistas e supervisão", purpose: "Recorrência, causa raiz e escalonamento técnico", members: ["u-supervisor-1"] }
        ],
        templates: [
            {
                id: "template-incident",
                name: "Incidente em loja",
                type: "Incidente",
                category: "PDV",
                description: "Informe o serviço afetado, quantas pessoas ou caixas foram impactados e quando o problema começou."
            },
            {
                id: "template-access",
                name: "Acesso ou novo usuário",
                type: "Solicitação de serviço",
                category: "Usuários",
                description: "Descreva a unidade, o sistema necessário e o perfil de acesso solicitado. Não inclua senhas."
            }
        ],
        knowledge: [
            { id: "KB-001", title: "Conferir o fechamento do caixa", category: "PDV", summary: "Revise as pendências e gere novamente o resumo de fechamento antes de abrir um chamado.", steps: ["Confirme se todas as vendas foram sincronizadas.", "Revise a lista de pendências do caixa.", "Gere o resumo e peça validação ao responsável da unidade."] },
            { id: "KB-002", title: "Primeiros passos para conexão da loja", category: "Internet", summary: "Verifique se a falha afeta uma estação, um setor ou toda a unidade.", steps: ["Confirme o estado dos equipamentos de rede sem reiniciar dispositivos críticos.", "Registre início, abrangência e serviços afetados.", "Se toda a loja estiver sem conexão no horário de venda, classifique como impacto alto e encaminhe a N2."] }
        ],
        rules: [
            { name: "PDV ou internet em horário de operação", condition: "Categoria PDV ou Internet durante horário comercial, ou impacto e urgência altos", destination: "N2 · Suporte especializado", reason: "Elevar a prioridade e reduzir o tempo de resposta quando as vendas estão em risco." }
        ],
        tickets,
        auditLog: [
            { at: new Date().toISOString(), user: "Sistema", text: "Ambiente inicializado com suporte a Login e Cadastro." }
        ]
    };
}

/* Lê os dados salvos no navegador */
function loadData() {
    try {
        const saved = localStorage.getItem(CONFIG.storageKey);
        if (!saved) return createInitialData();
        const parsed = JSON.parse(saved);
        if (!parsed || parsed.version !== 1 || !Array.isArray(parsed.tickets) || !Array.isArray(parsed.users)) {
            throw new Error("Formato dos dados locais não reconhecido.");
        }
        return parsed;
    } catch (error) {
        const initial = createInitialData();
        initial.storageWarning = `Não foi possível ler os dados anteriores: ${error.message}`;
        return initial;
    }
}

/* Salva o estado atual */
function saveData() {
    try {
        localStorage.setItem(CONFIG.storageKey, JSON.stringify(data));
        return true;
    } catch (error) {
        showToast(`Não foi possível salvar neste navegador: ${error.message}`, "error");
        return false;
    }
}

/* Sanitiza texto HTML */
function escapeHtml(value = "") {
    return String(value).replace(/[&<>"']/g, (character) => ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        "\"": "&quot;",
        "'": "&#39;"
    })[character]);
}

/* Retorna o usuário logado ou null caso não haja sessão ativa */
function currentUser() {
    if (!data.currentUserId) return null;
    return data.users.find((user) => user.id === data.currentUserId) || null;
}

function isAdmin() {
    const user = currentUser();
    return user ? user.role === "Supervisor" : false;
}

function canManageTickets() {
    const user = currentUser();
    return user ? user.role !== "Solicitante" : false;
}

/* Funções utilitárias de prioridades e datas */
function calculatePriority(impact, urgency) {
    const impactIndex = CONFIG.impactLevels.indexOf(impact);
    const urgencyIndex = CONFIG.urgencyLevels.indexOf(urgency);
    const matrix = [
        ["Baixa", "Baixa", "Média"],
        ["Baixa", "Média", "Alta"],
        ["Média", "Alta", "Crítica"]
    ];
    return matrix[impactIndex]?.[urgencyIndex] || "Média";
}

function isRetailBusinessHours(date = new Date()) {
    const weekday = date.getDay();
    const hour = date.getHours();
    return CONFIG.businessHours.days.includes(weekday) && hour >= CONFIG.businessHours.start && hour < CONFIG.businessHours.end;
}

function calculateTicketPriority(impact, urgency, category, date = new Date()) {
    const matrixPriority = calculatePriority(impact, urgency);
    const timeCriticalCategory = ["PDV", "Internet"].includes(category);
    if (timeCriticalCategory && isRetailBusinessHours(date) && priorityRank(matrixPriority) < priorityRank("Alta")) {
        return "Alta";
    }
    return matrixPriority;
}

function routeTicket(ticket) {
    const isRecurring = /recorrente|reincidente|causa raiz/i.test(`${ticket.subject} ${ticket.description}`);
    if (isRecurring) return "N3";
    if (["PDV", "Internet"].includes(ticket.category) && priorityRank(ticket.priority) >= priorityRank("Alta")) return "N2";
    return "N1";
}

function usersForLevel(level) {
    return data.users.filter((user) => user.level === level && user.role !== "Solicitante");
}

function getUserName(userId) {
    return data.users.find((user) => user.id === userId)?.name || "Não atribuído";
}

function initials(name = "") {
    return name.split(/\s+/).slice(0, 2).map((part) => part[0] || "").join("").toUpperCase();
}

function formatDate(value) {
    return new Intl.DateTimeFormat("pt-BR", { dateStyle: "short", timeStyle: "short" }).format(new Date(value));
}

function relativeTime(value) {
    const minutes = Math.max(0, Math.floor((Date.now() - new Date(value).getTime()) / 60_000));
    if (minutes < 1) return "agora";
    if (minutes < 60) return `há ${minutes} min`;
    const hours = Math.floor(minutes / 60);
    if (hours < 24) return `há ${hours} h`;
    return `há ${Math.floor(hours / 24)} dia(s)`;
}

function hoursMinutes(minutes) {
    if (minutes < 60) return `${minutes} min`;
    const hours = Math.floor(minutes / 60);
    const remaining = minutes % 60;
    return remaining ? `${hours} h ${remaining} min` : `${hours} h`;
}

function slaFor(ticket) {
    const closed = ["Resolvido", "Encerrado"].includes(ticket.status);
    const deadline = new Date(ticket.resolutionDueAt).getTime();
    const remaining = Math.ceil((deadline - Date.now()) / 60_000);
    const responseRemaining = Math.ceil((new Date(ticket.responseDueAt).getTime() - Date.now()) / 60_000);
    const responseLate = !closed && !ticket.firstResponseAt && responseRemaining < 0;
    return {
        closed,
        remaining,
        late: !closed && remaining < 0,
        responseLate,
        responseLabel: ticket.firstResponseAt
            ? `Respondido ${relativeTime(ticket.firstResponseAt)}`
            : responseRemaining < 0
                ? `1ª resposta atrasada ${hoursMinutes(Math.abs(responseRemaining))}`
                : `1ª resposta em ${hoursMinutes(responseRemaining)}`,
        label: closed ? "Concluído" : remaining < 0 ? `Atrasado ${hoursMinutes(Math.abs(remaining))}` : `Restam ${hoursMinutes(remaining)}`
    };
}

function addHistory(ticket, text, actor = currentUser()?.name || "Sistema") {
    ticket.history.push({ at: new Date().toISOString(), user: actor, text });
    data.auditLog.unshift({ at: new Date().toISOString(), user: actor, text: `${ticket.id}: ${text}` });
}

function showToast(message, kind = "success") {
    window.clearTimeout(toastTimeout);
    toast.textContent = message;
    toast.className = `toast toast--visible toast--${kind}`;
    toastTimeout = window.setTimeout(() => {
        toast.className = "toast";
    }, 4200);
}

// ========================================================
// SISTEMA DE AUTENTICAÇÃO E SESSÃO (LOGIN & CRIAR CONTA)
// ========================================================

/* Desenha a área de sessão no topo e no menu lateral */
function renderUserSession() {
    const userSessionContainer = document.querySelector("#user-session");
    const sidebarAvatar = document.querySelector("#sidebar-user-avatar");
    const sidebarName = document.querySelector("#sidebar-user-name");
    const sidebarRole = document.querySelector("#sidebar-user-role");

    const user = currentUser();

    if (user) {
        const roleLabel = user.role === "Supervisor"
            ? "Supervisor(a)"
            : user.level ? `${user.role} (${user.level})` : user.role;

        // Usuário logado
        userSessionContainer.innerHTML = `
            <div class="user-session-info">
                <span class="user-avatar">${escapeHtml(initials(user.name))}</span>
                <span class="user-name-role">
                    <strong>${escapeHtml(user.name)}</strong>
                    <small>${escapeHtml(roleLabel)}</small>
                </span>
                <button id="btn-logout" class="secondary-button btn-logout" type="button">Sair</button>
            </div>
        `;

        if (sidebarAvatar) sidebarAvatar.textContent = initials(user.name);
        if (sidebarName) sidebarName.textContent = user.name;
        if (sidebarRole) sidebarRole.textContent = `${user.role} · ${user.store}`;

        document.querySelector("#btn-logout")?.addEventListener("click", handleLogout);
    } else {
        // Usuário não logado
        userSessionContainer.innerHTML = `
            <button id="btn-open-auth" class="primary-button" type="button">
                <span>👤</span> Entrar / Criar Conta
            </button>
        `;

        if (sidebarAvatar) sidebarAvatar.textContent = "--";
        if (sidebarName) sidebarName.textContent = "Não autenticado";
        if (sidebarRole) sidebarRole.textContent = "Faça login para acessar";

        document.querySelector("#btn-open-auth")?.addEventListener("click", () => openAuthModal("login"));
    }
}

/* Abre o modal de autenticação (na aba especificada) */
function openAuthModal(tab = "login") {
    populateStoreSelect();
    switchAuthTab(tab);
    authDialog.showModal();
}

function closeAuthModal() {
    authDialog.close();
}

function switchAuthTab(tab) {
    const loginForm = document.querySelector("#login-form");
    const signupForm = document.querySelector("#signup-form");
    const tabBtns = document.querySelectorAll("[data-auth-tab]");

    tabBtns.forEach(btn => {
        btn.classList.toggle("auth-tab-btn--active", btn.dataset.authTab === tab);
    });

    if (tab === "login") {
        loginForm.style.display = "block";
        signupForm.style.display = "none";
    } else {
        loginForm.style.display = "none";
        signupForm.style.display = "block";
    }
}

function populateStoreSelect() {
    const storeSelect = document.querySelector("#signup-store-select");
    if (storeSelect) {
        storeSelect.innerHTML = CONFIG.stores.map(store => `<option value="${escapeHtml(store)}">${escapeHtml(store)}</option>`).join("");
    }
}

/* Função para realizar LOGIN */
function handleLogin(email, password) {
    const user = data.users.find(u => u.email.toLowerCase() === email.trim().toLowerCase() && u.password === password);

    if (!user) {
        showToast("E-mail ou senha incorretos.", "error");
        return;
    }

    data.currentUserId = user.id;
    saveData();
    closeAuthModal();
    render();
    showToast(`Bem-vindo(a), ${user.name}!`);
}

/* Função para CRIAR CONTA (CADASTRO) */
function handleSignup(name, email, password, role, level, store) {
    // Verifica se e-mail já existe
    const exists = data.users.some(u => u.email.toLowerCase() === email.trim().toLowerCase());
    if (exists) {
        showToast("Este e-mail já está cadastrado. Faça login.", "error");
        return;
    }

    // Cria novo registro de usuário
    const newUser = {
        id: `u-user-${Date.now()}`,
        name: name.trim(),
        email: email.trim().toLowerCase(),
        password: password,
        role: role,
        level: role === "Solicitante" ? null : (level || "N1"),
        store: store
    };

    data.users.push(newUser);

    // Se o usuário criado for Técnico/Supervisor, vincula à equipe correspondente
    if (newUser.role !== "Solicitante") {
        const team = data.teams.find(t => t.level === newUser.level);
        if (team && !team.members.includes(newUser.id)) {
            team.members.push(newUser.id);
        }
    }

    // Define como usuário ativo e salva
    data.currentUserId = newUser.id;
    data.auditLog.unshift({
        at: new Date().toISOString(),
        user: newUser.name,
        text: `Novo usuário cadastrado (${newUser.role} - ${newUser.store}).`
    });

    saveData();
    closeAuthModal();
    render();
    showToast(`Conta criada com sucesso! Bem-vindo(a), ${newUser.name}.`);
}

/* Função para LOGOUT */
function handleLogout() {
    data.currentUserId = null;
    saveData();
    render();
    showToast("Você saiu do sistema.");
    openAuthModal("login");
}

// ========================================================
// RENDERIZAÇÃO E FLUXO DAS PÁGINAS
// ========================================================

function renderNavigation() {
    const adminOnly = new Set(["people", "rules"]);
    document.querySelectorAll("[data-page]").forEach((button) => {
        const page = button.dataset.page;
        if (button.matches(".side-nav__link")) {
            button.hidden = adminOnly.has(page) && !isAdmin();
            button.classList.toggle("side-nav__link--active", page === currentPage);
            button.removeAttribute("aria-current");
            if (page === currentPage) button.setAttribute("aria-current", "page");
        }
    });
    document.querySelector("#current-page-name").textContent = CONFIG.pageNames[currentPage] || CONFIG.pageNames.dashboard;
}

function renderOpenCount() {
    const visible = ticketsVisibleToCurrentUser();
    const count = visible.filter((ticket) => !["Resolvido", "Encerrado"].includes(ticket.status)).length;
    document.querySelector("[data-open-ticket-count]").textContent = count;
}

function ticketsVisibleToCurrentUser() {
    const user = currentUser();
    if (!user) return [];
    return canManageTickets()
        ? data.tickets
        : data.tickets.filter((ticket) => ticket.requesterId === user.id);
}

function priorityBadge(priority) {
    const slug = CONFIG.priorities[priority]?.color || "medium";
    return `<span class="priority priority--${slug}"><i></i>${escapeHtml(priority)}</span>`;
}

function statusBadge(status) {
    const classes = {
        "Novo": "new",
        "Em triagem": "triage",
        "Em atendimento": "progress",
        "Aguardando solicitante": "waiting",
        "Suporte remoto": "remote",
        "Resolvido": "resolved",
        "Encerrado": "closed"
    };
    return `<span class="status status--${classes[status] || "new"}">${escapeHtml(status)}</span>`;
}

function renderDashboard() {
    const user = currentUser();
    if (!user) {
        view.innerHTML = renderUnauthenticatedState();
        return;
    }

    const tickets = ticketsVisibleToCurrentUser();
    const open = tickets.filter((ticket) => !["Resolvido", "Encerrado"].includes(ticket.status));
    const resolved = tickets.filter((ticket) => ["Resolvido", "Encerrado"].includes(ticket.status));
    const breached = open.filter((ticket) => slaFor(ticket).late);
    const categoriesWithOpenTickets = new Set(open.map((ticket) => ticket.category)).size;

    view.innerHTML = `
        <section class="page-heading" aria-labelledby="page-title">
            <div><p class="breadcrumb-caption">PAINEL DE ATENDIMENTO</p><h1 id="page-title">Visão geral</h1>
                <p class="page-heading__description">Chamados, prazos e serviços das unidades da Rede Varejo.</p></div>
            <button class="primary-button" type="button" data-action="new-ticket"><span aria-hidden="true">＋</span> Abrir chamado</button>
        </section>
        <section class="stats-grid" aria-label="Resumo dos chamados">
            <article class="stat-card"><div class="stat-card__top"><span>Chamados em aberto</span><span class="stat-icon stat-icon--blue" aria-hidden="true">▤</span></div><p class="stat-card__value">${open.length}</p><p class="stat-card__foot">Fila visível para o perfil atual</p></article>
            <article class="stat-card"><div class="stat-card__top"><span>Resolvidos</span><span class="stat-icon stat-icon--green" aria-hidden="true">✓</span></div><p class="stat-card__value">${resolved.length}</p><p class="stat-card__foot">Com solução registrada antes do encerramento</p></article>
            <article class="stat-card"><div class="stat-card__top"><span>Prazo de solução vencido</span><span class="stat-icon stat-icon--amber" aria-hidden="true">!</span></div><p class="stat-card__value">${breached.length}</p><p class="stat-card__foot stat-card__foot--warning">Requerem escalonamento ou atualização</p></article>
            <article class="stat-card"><div class="stat-card__top"><span>Categorias em uso</span><span class="stat-icon stat-icon--purple" aria-hidden="true">▧</span></div><p class="stat-card__value">${categoriesWithOpenTickets}/${data.categories.length}</p><p class="stat-card__foot">${data.stores.length} unidades, incluindo matriz</p></article>
        </section>
        <section class="content-panel dashboard-panel" aria-labelledby="recent-title">
            <div class="panel-heading"><div><h2 id="recent-title">Chamados que precisam de atenção</h2><p>Prioridade, prazo e próxima ação da equipe</p></div><button class="secondary-button" type="button" data-page="tickets">Ver fila completa <span aria-hidden="true">→</span></button></div>
            ${renderTicketTable(open.slice().sort((a, b) => priorityRank(b.priority) - priorityRank(a.priority)).slice(0, 5))}
        </section>`;
}

function priorityRank(priority) {
    return ({ "Crítica": 4, "Alta": 3, "Média": 2, "Baixa": 1 })[priority] || 0;
}

function renderTicketTable(tickets) {
    if (!tickets.length) return `<div class="empty-state"><strong>Nenhum chamado encontrado</strong><span>Altere o filtro ou abra uma nova solicitação.</span></div>`;
    return `<div class="table-scroll"><table class="ticket-table">
        <thead><tr><th scope="col">Chamado</th><th scope="col">Assunto e categoria</th><th scope="col">Unidade</th><th scope="col">Solicitante</th><th scope="col">Prioridade</th><th scope="col">Nível</th><th scope="col">Status</th><th scope="col">Prazo</th></tr></thead>
        <tbody>${tickets.map((ticket) => {
            const sla = slaFor(ticket);
            return `<tr>
                <td><button class="table-link ticket-id" type="button" data-action="open-ticket" data-ticket-id="${escapeHtml(ticket.id)}">#${escapeHtml(ticket.id)}</button></td>
                <td><span class="ticket-subject">${escapeHtml(ticket.subject)}</span><small class="ticket-category">${escapeHtml(ticket.category)} · ${escapeHtml(ticket.type)}</small></td>
                <td>${escapeHtml(ticket.store)}</td>
                <td>${escapeHtml(getUserName(ticket.requesterId))}</td>
                <td>${priorityBadge(ticket.priority)}</td>
                <td><span class="level-tag">${escapeHtml(ticket.level)}</span></td>
                <td>${statusBadge(ticket.status)}</td>
                <td class="sla-cell ${sla.late ? "sla-cell--late" : ""}">${escapeHtml(sla.label)}</td>
            </tr>`;
        }).join("")}</tbody>
    </table></div>`;
}

function renderTickets() {
    if (!currentUser()) {
        view.innerHTML = renderUnauthenticatedState();
        return;
    }

    const search = globalSearch.value.trim().toLocaleLowerCase("pt-BR");
    let tickets = ticketsVisibleToCurrentUser();
    if (ticketFilter !== "Todos") {
        const filterStatus = {
            "Abertos": (ticket) => !["Resolvido", "Encerrado"].includes(ticket.status),
            "Em atendimento": (ticket) => ["Em atendimento", "Suporte remoto"].includes(ticket.status),
            "Aguardando": (ticket) => ticket.status === "Aguardando solicitante",
            "Resolvidos": (ticket) => ["Resolvido", "Encerrado"].includes(ticket.status)
        }[ticketFilter];
        if (filterStatus) tickets = tickets.filter(filterStatus);
    }
    if (search) {
        tickets = tickets.filter((ticket) =>
            [ticket.id, ticket.subject, ticket.description, ticket.store, ticket.category, ticket.status, ticket.scenario, getUserName(ticket.requesterId)]
                .some((value) => String(value || "").toLocaleLowerCase("pt-BR").includes(search))
        );
    }
    tickets = tickets.slice().sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

    view.innerHTML = `
        <section class="page-heading" aria-labelledby="page-title"><div><p class="breadcrumb-caption">ESPAÇO DE TRABALHO</p><h1 id="page-title">${isAdmin() ? "Fila de chamados" : "Meus chamados"}</h1>
            <p class="page-heading__description">${isAdmin() ? "Acompanhe responsáveis, prioridade, prazo e histórico." : "Acompanhe as solicitações registradas pelo seu perfil."}</p></div>
            <button class="primary-button" type="button" data-action="new-ticket"><span aria-hidden="true">＋</span> Abrir chamado</button></section>
        <section class="content-panel tickets-panel">
            <div class="panel-heading"><div><h2>Solicitações</h2><p>${tickets.length} registro(s) encontrados</p></div>
                <label class="inline-search"><span aria-hidden="true">⌕</span><input id="ticket-search" type="search" value="${escapeHtml(globalSearch.value)}" placeholder="Filtrar chamados..." aria-label="Filtrar chamados"></label></div>
            <div class="filter-row" aria-label="Filtrar por status">
                ${["Todos", "Abertos", "Em atendimento", "Aguardando", "Resolvidos"].map((filter) => `<button class="filter-chip ${ticketFilter === filter ? "filter-chip--active" : ""}" type="button" data-filter="${escapeHtml(filter)}" aria-pressed="${ticketFilter === filter}">${escapeHtml(filter)} <span>${filterCount(filter)}</span></button>`).join("")}
            </div>
            ${renderTicketTable(tickets)}
            <div class="table-footer"><span>Histórico preservado por chamado</span><span>${tickets.length} chamado(s)</span></div>
        </section>`;
}

function filterCount(filter) {
    const tickets = ticketsVisibleToCurrentUser();
    if (filter === "Todos") return tickets.length;
    if (filter === "Abertos") return tickets.filter((ticket) => !["Resolvido", "Encerrado"].includes(ticket.status)).length;
    if (filter === "Em atendimento") return tickets.filter((ticket) => ["Em atendimento", "Suporte remoto"].includes(ticket.status)).length;
    if (filter === "Aguardando") return tickets.filter((ticket) => ticket.status === "Aguardando solicitante").length;
    return tickets.filter((ticket) => ["Resolvido", "Encerrado"].includes(ticket.status)).length;
}

function renderCatalog() {
    view.innerHTML = `
        <section class="page-heading" aria-labelledby="page-title"><div><p class="breadcrumb-caption">CONFIGURAÇÃO DO SERVIÇO</p><h1 id="page-title">Catálogo e modelos</h1><p class="page-heading__description">Tipos, categorias e formulários usados pelas unidades.</p></div></section>
        <section class="content-panel section-panel"><div class="panel-heading"><div><h2>Tipos de atendimento</h2></div></div>
            <div class="type-grid"><article><span class="type-mark type-mark--red">!</span><div><strong>Incidente</strong><p>Interrupção ou degradação de um serviço existente.</p></div></article><article><span class="type-mark type-mark--blue">＋</span><div><strong>Solicitação de serviço</strong><p>Pedido padronizado, como acesso ou cadastro.</p></div></article><article><span class="type-mark type-mark--purple">↗</span><div><strong>Mudança/melhoria</strong><p>Evolução planejada de sistema.</p></div></article></div>
        </section>
        <section class="content-panel section-panel"><div class="panel-heading"><div><h2>Formulários padronizados</h2></div></div>
            <div class="template-grid">${data.templates.map((template) => `<article class="template-card"><div class="template-card__top"><span class="template-icon" aria-hidden="true">▤</span><span class="type-pill">${escapeHtml(template.type)}</span></div><h3>${escapeHtml(template.name)}</h3><p>${escapeHtml(template.description)}</p><button class="secondary-button" type="button" data-action="use-template" data-template-id="${escapeHtml(template.id)}">Preencher modelo <span aria-hidden="true">→</span></button></article>`).join("")}</div>
        </section>`;
}

function renderKnowledge() {
    view.innerHTML = `
        <section class="page-heading" aria-labelledby="page-title"><div><p class="breadcrumb-caption">AUTOATENDIMENTO E SUPORTE N1</p><h1 id="page-title">Base de conhecimento</h1></div></section>
        <section class="content-panel section-panel"><div class="panel-heading"><div><h2>Artigos disponíveis</h2></div></div>
            <div class="knowledge-grid">${data.knowledge.map((article) => `<article class="knowledge-card"><div class="knowledge-card__top"><span class="kb-id">${escapeHtml(article.id)}</span><span class="type-pill">${escapeHtml(article.category)}</span></div><h3>${escapeHtml(article.title)}</h3><p>${escapeHtml(article.summary)}</p><details><summary>Ver passo a passo</summary><ol>${article.steps.map((step) => `<li>${escapeHtml(step)}</li>`).join("")}</ol></details></article>`).join("")}</div>
        </section>`;
}

function renderPeople() {
    if (!isAdmin()) {
        view.innerHTML = renderAccessDenied();
        return;
    }
    view.innerHTML = `
        <section class="page-heading" aria-labelledby="page-title"><div><p class="breadcrumb-caption">ADMINISTRAÇÃO</p><h1 id="page-title">Usuários e equipes</h1></div></section>
        <section class="content-panel section-panel"><div class="panel-heading"><div><h2>Usuários cadastrados</h2></div><span class="type-pill">${data.users.length} usuários</span></div>
            <div class="table-scroll"><table class="ticket-table users-table"><thead><tr><th scope="col">Nome</th><th scope="col">E-mail</th><th scope="col">Perfil</th><th scope="col">Nível</th><th scope="col">Unidade</th></tr></thead>
                <tbody>${data.users.map((user) => `<tr><td><span class="requester"><span class="requester__avatar">${escapeHtml(initials(user.name))}</span>${escapeHtml(user.name)}</span></td><td>${escapeHtml(user.email || "-")}</td><td>${escapeHtml(user.role)}</td><td>${escapeHtml(user.level || "Solicitante")}</td><td>${escapeHtml(user.store)}</td></tr>`).join("")}</tbody></table></div>
        </section>`;
}

function renderRules() {
    if (!isAdmin()) {
        view.innerHTML = renderAccessDenied();
        return;
    }
    view.innerHTML = `
        <section class="page-heading" aria-labelledby="page-title"><div><p class="breadcrumb-caption">PROCESSO E GOVERNANÇA</p><h1 id="page-title">Prioridades e regras</h1></div></section>
        <section class="rules-grid">
            <article class="content-panel section-panel"><div class="panel-heading"><div><h2>Matriz de prioridade</h2></div></div>
                <div class="table-scroll"><table class="matrix-table"><thead><tr><th scope="col">Impacto ↓ · Urgência →</th>${CONFIG.urgencyLevels.map((item) => `<th scope="col">${escapeHtml(item)}</th>`).join("")}</tr></thead>
                <tbody>${CONFIG.impactLevels.map((impact) => `<tr><th scope="row">${escapeHtml(impact)}</th>${CONFIG.urgencyLevels.map((urgency) => `<td>${priorityBadge(calculatePriority(impact, urgency))}</td>`).join("")}</tr>`).join("")}</tbody></table></div>
            </article>
        </section>`;
}

function renderReports() {
    if (!isAdmin()) {
        view.innerHTML = renderAccessDenied();
        return;
    }
    const tickets = ticketsVisibleToCurrentUser();
    const done = tickets.filter((ticket) => ["Resolvido", "Encerrado"].includes(ticket.status));

    view.innerHTML = `
        <section class="page-heading" aria-labelledby="page-title"><div><p class="breadcrumb-caption">ACOMPANHAMENTO E EVIDÊNCIAS</p><h1 id="page-title">Relatórios e testes</h1></div></section>
        <section class="stats-grid report-stats">
            <article class="stat-card"><div class="stat-card__top"><span>Total de chamados</span></div><p class="stat-card__value">${tickets.length}</p></article>
            <article class="stat-card"><div class="stat-card__top"><span>Concluídos</span></div><p class="stat-card__value">${done.length}</p></article>
        </section>`;
}

function renderAccessDenied() {
    return `<section class="empty-state empty-state--large"><strong>Acesso restrito</strong><span>Sua conta atual não possui privilégios de Supervisor.</span></section>`;
}

function renderUnauthenticatedState() {
    return `
        <section class="empty-state empty-state--large">
            <strong>Você não está autenticado</strong>
            <span>Faça login ou crie uma conta para visualizar e abrir chamados.</span>
            <br>
            <button class="primary-button" type="button" onclick="openAuthModal('login')">Entrar ou Criar Conta</button>
        </section>
    `;
}

function render() {
    renderUserSession();
    renderNavigation();
    renderOpenCount();
    const pages = {
        dashboard: renderDashboard,
        tickets: renderTickets,
        catalog: renderCatalog,
        knowledge: renderKnowledge,
        people: renderPeople,
        rules: renderRules,
        reports: renderReports
    };
    (pages[currentPage] || renderDashboard)();
}

/* Prepara formulário para NOVO CHAMADO */
function openNewTicket(templateId = "") {
    const user = currentUser();
    if (!user) {
        showToast("Faça login para abrir um chamado.", "error");
        openAuthModal("login");
        return;
    }

    dialogMode = "new";
    selectedTicketId = null;
    const template = data.templates.find((item) => item.id === templateId);
    document.querySelector("#dialog-title").textContent = template ? `Novo chamado · ${template.name}` : "Novo chamado";
    dialog.querySelector(".dialog-actions [type='submit']").textContent = "Registrar chamado";
    dialogContent.innerHTML = renderNewTicketForm(template);
    dialog.showModal();
    if (user.role !== "Técnico") startTicketIdleTimer();
}

function renderNewTicketForm(template) {
    const user = currentUser();
    const shouldUseIdleTimer = user.role !== "Técnico";

    return `
        <p class="form-intro">Preencha os dados da solicitação. A prioridade e o primeiro nível serão calculados automaticamente.</p>
        ${shouldUseIdleTimer ? '<p class="idle-timer" data-idle-timer role="timer" aria-live="off"></p>' : ""}
        <div class="form-grid">
            <label class="form-field form-field--wide">Assunto <span>*</span><input name="subject" required maxlength="120" placeholder="Ex.: PDV não conecta à impressora"></label>
            <label class="form-field">Tipo <span>*</span><select name="type" required>${CONFIG.types.map((type) => `<option ${type === (template?.type || "Incidente") ? "selected" : ""}>${escapeHtml(type)}</option>`).join("")}</select></label>
            <label class="form-field">Categoria <span>*</span><select name="category" required>${data.categories.map((category) => `<option ${category === (template?.category || "PDV") ? "selected" : ""}>${escapeHtml(category)}</option>`).join("")}</select></label>
            <label class="form-field">Unidade <span>*</span><select name="store" required>${data.stores.map((store) => `<option ${store === user.store ? "selected" : ""}>${escapeHtml(store)}</option>`).join("")}</select></label>
            <label class="form-field">Solicitante <span>*</span>
                <input type="text" disabled value="${escapeHtml(user.name)} (${escapeHtml(user.store)})">
                <input type="hidden" name="requesterId" value="${escapeHtml(user.id)}">
            </label>
            <label class="form-field">Impacto <span>*</span><select name="impact" required>${CONFIG.impactLevels.map((value) => `<option ${value === "Médio" ? "selected" : ""}>${escapeHtml(value)}</option>`).join("")}</select></label>
            <label class="form-field">Urgência <span>*</span><select name="urgency" required>${CONFIG.urgencyLevels.map((value) => `<option ${value === "Média" ? "selected" : ""}>${escapeHtml(value)}</option>`).join("")}</select></label>
            <label class="form-field form-field--wide">Descrição <span>*</span><textarea name="description" rows="4" required maxlength="2000" placeholder="Descreva o que aconteceu...">${escapeHtml(template?.description || "")}</textarea></label>
        </div>
        <p class="priority-preview" data-priority-preview aria-live="polite">Prioridade calculada: ${calculatePriority("Médio", "Média")}</p>`;
}

function clearTicketIdleTimer() {
    window.clearTimeout(ticketIdleTimeout);
    window.clearInterval(ticketIdleCountdown);
    ticketIdleTimeout = null;
    ticketIdleCountdown = null;
    ticketIdleDeadline = null;
}

function startTicketIdleTimer() {
    clearTicketIdleTimer();
    ticketIdleDeadline = Date.now() + TICKET_IDLE_TIMEOUT_MS;
    const timerLabel = dialogContent.querySelector("[data-idle-timer]");
    if (!timerLabel) return;

    const updateCountdown = () => {
        const remainingSeconds = Math.max(0, Math.ceil((ticketIdleDeadline - Date.now()) / 1000));
        const minutes = String(Math.floor(remainingSeconds / 60)).padStart(2, "0");
        const seconds = String(remainingSeconds % 60).padStart(2, "0");
        timerLabel.textContent = `A tela será fechada em ${minutes}:${seconds} se ficar sem atividade.`;
    };

    updateCountdown();
    ticketIdleTimeout = window.setTimeout(() => {
        if (dialog.open && dialogMode === "new") {
            dialog.close();
            showToast("Formulário fechado após 1 minuto sem atividade.", "error");
        }
    }, TICKET_IDLE_TIMEOUT_MS);
    ticketIdleCountdown = window.setInterval(updateCountdown, 1000);
}

/* Criação do chamado */
function createTicketFromForm(form) {
    const user = currentUser();
    if (!user) return;

    const values = new FormData(form);
    const impact = String(values.get("impact"));
    const urgency = String(values.get("urgency"));
    const category = String(values.get("category"));
    const createdAt = new Date();
    const matrixPriority = calculatePriority(impact, urgency);
    const priority = calculateTicketPriority(impact, urgency, category, createdAt);

    const newTicket = {
        id: `VJ-${data.nextTicketNumber}`,
        subject: String(values.get("subject")).trim(),
        description: String(values.get("description")).trim(),
        type: String(values.get("type")),
        category,
        store: String(values.get("store")),
        requesterId: user.id,
        impact,
        urgency,
        priority,
        level: "N1",
        assigneeId: null,
        status: "Novo",
        remoteAuthorized: false,
        solution: "",
        scenario: "",
        createdAt: createdAt.toISOString(),
        firstResponseAt: null,
        history: []
    };

    newTicket.level = routeTicket(newTicket);
    const team = data.teams.find((item) => item.level === newTicket.level);
    newTicket.assigneeId = team?.members[0] || null;

    const sla = CONFIG.priorities[priority];
    newTicket.responseDueAt = new Date(Date.now() + sla.responseMinutes * 60_000).toISOString();
    newTicket.resolutionDueAt = new Date(Date.now() + sla.resolutionMinutes * 60_000).toISOString();

    newTicket.history.push({
        at: newTicket.createdAt,
        user: user.name,
        text: `Chamado registrado. Prioridade ${priority}, encaminhado para ${newTicket.level}.`
    });

    data.tickets.unshift(newTicket);
    data.nextTicketNumber += 1;
    data.auditLog.unshift({ at: new Date().toISOString(), user: user.name, text: `${newTicket.id}: chamado criado.` });

    saveData();
    dialog.close();
    currentPage = "tickets";
    render();
    showToast(`Chamado #${newTicket.id} registrado com sucesso.`);
}

/* Detalhes do Chamado */
function openTicketDetails(ticketId) {
    const user = currentUser();
    if (!user) {
        openAuthModal("login");
        return;
    }

    const ticket = data.tickets.find((item) => item.id === ticketId);
    if (!ticket) {
        showToast("O chamado selecionado não foi encontrado.", "error");
        return;
    }
    if (!canManageTickets() && ticket.requesterId !== user.id) {
        showToast("Este perfil só pode consultar os próprios chamados.", "error");
        return;
    }
    dialogMode = "details";
    selectedTicketId = ticketId;
    document.querySelector("#dialog-title").textContent = `Chamado #${ticket.id}`;
    dialog.querySelector(".dialog-actions [type='submit']").textContent = canManageTickets() ? "Salvar andamento" : "Fechar";
    dialogContent.innerHTML = renderTicketDetails(ticket);
    dialog.showModal();
}

function renderTicketDetails(ticket) {
    const canEdit = canManageTickets();
    const sla = slaFor(ticket);
    const nextLevel = CONFIG.supportLevels[CONFIG.supportLevels.indexOf(ticket.level) + 1];

    return `
        <div class="detail-summary"><div><span class="ticket-id">#${escapeHtml(ticket.id)}</span>${priorityBadge(ticket.priority)} ${statusBadge(ticket.status)}</div><h3>${escapeHtml(ticket.subject)}</h3><p>${escapeHtml(ticket.description)}</p></div>
        <div class="detail-facts"><span><small>Unidade</small><strong>${escapeHtml(ticket.store)}</strong></span><span><small>Solicitante</small><strong>${escapeHtml(getUserName(ticket.requesterId))}</strong></span><span><small>Tipo · categoria</small><strong>${escapeHtml(ticket.type)} · ${escapeHtml(ticket.category)}</strong></span><span><small>Prazo de solução</small><strong class="${sla.late ? "text-danger" : ""}">${escapeHtml(sla.label)}</strong></span></div>
        ${canEdit ? `<div class="form-grid detail-controls">
            <label class="form-field">Status<select name="status">${CONFIG.statuses.map((status) => `<option ${status === ticket.status ? "selected" : ""}>${escapeHtml(status)}</option>`).join("")}</select></label>
            <label class="form-field">Nível<select name="level">${CONFIG.supportLevels.map((level) => `<option ${level === ticket.level ? "selected" : ""}>${escapeHtml(level)}</option>`).join("")}</select></label>
            <label class="form-field form-field--wide">Solução<textarea name="solution" rows="3" placeholder="Registre os passos da solução...">${escapeHtml(ticket.solution || "")}</textarea></label>
        </div>` : `<div class="detail-solution"><strong>Solução registrada</strong><p>${ticket.solution ? escapeHtml(ticket.solution) : "Ainda não há uma solução registrada."}</p></div>`}
        <section class="history-section"><h3>Histórico</h3><ol class="ticket-history">${ticket.history.slice().reverse().map((entry) => `<li><span class="history-dot"></span><div><p>${escapeHtml(entry.text)}</p><small>${escapeHtml(entry.user)} · ${formatDate(entry.at)}</small></div></li>`).join("")}</ol></section>`;
}

function updateTicketFromForm(form) {
    const ticket = data.tickets.find((item) => item.id === selectedTicketId);
    if (!ticket) return;

    const values = new FormData(form);
    const nextStatus = String(values.get("status"));
    const nextLevel = String(values.get("level"));
    const solution = String(values.get("solution") || "").trim();

    if (["Resolvido", "Encerrado"].includes(nextStatus) && !solution) {
        showToast("Registre a solução antes de resolver ou encerrar o chamado.", "error");
        return;
    }

    if (ticket.status !== nextStatus) {
        addHistory(ticket, `Status alterado para "${nextStatus}".`);
        ticket.status = nextStatus;
    }
    if (ticket.level !== nextLevel) {
        addHistory(ticket, `Chamado escalonado para ${nextLevel}.`);
        ticket.level = nextLevel;
    }
    if (solution && ticket.solution !== solution) {
        addHistory(ticket, `Solução/diagnóstico: ${solution}`);
        ticket.solution = solution;
    }

    saveData();
    dialog.close();
    render();
    showToast(`Chamado #${ticket.id} atualizado.`);
}

function exportData() {
    const blob = new Blob([JSON.stringify(data, null, 2)], { type: "application/json" });
    const url = URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    link.download = `rede-varejo-backup-${new Date().toISOString().slice(0, 10)}.json`;
    document.body.append(link);
    link.click();
    link.remove();
    showToast("Backup exportado com sucesso.");
}

// ========================================================
// LISTENERS E EVENTOS
// ========================================================

document.addEventListener("click", (event) => {
    const closeButton = event.target.closest("[data-close-dialog]");
    if (closeButton) dialog.close();

    const closeAuthBtn = event.target.closest("#close-auth-modal-btn");
    if (closeAuthBtn) closeAuthModal();

    const authTabBtn = event.target.closest("[data-auth-tab]");
    if (authTabBtn) switchAuthTab(authTabBtn.dataset.authTab);

    const pageButton = event.target.closest("[data-page]");
    if (pageButton) {
        event.preventDefault();
        const page = pageButton.dataset.page;
        if (["people", "rules", "reports"].includes(page) && !isAdmin()) {
            showToast("Essa seção exige perfil de Supervisor.", "error");
            return;
        }
        currentPage = page;
        render();
        return;
    }

    const filterButton = event.target.closest("[data-filter]");
    if (filterButton) {
        ticketFilter = filterButton.dataset.filter;
        renderTickets();
        return;
    }

    const action = event.target.closest("[data-action]");
    if (!action) return;
    if (action.dataset.action === "new-ticket") openNewTicket();
    if (action.dataset.action === "use-template") openNewTicket(action.dataset.templateId);
    if (action.dataset.action === "open-ticket") openTicketDetails(action.dataset.ticketId);
});

dialog.addEventListener("close", clearTicketIdleTimer);
["pointerdown", "keydown", "input", "change", "touchstart", "scroll"].forEach((eventName) => {
    dialog.addEventListener(eventName, () => {
        const user = currentUser();
        if (dialog.open && dialogMode === "new" && user && user.role !== "Técnico") {
            startTicketIdleTimer();
        }
    });
});

/* Listener dos formulários de Login e Cadastro */
document.querySelector("#login-form").addEventListener("submit", (e) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    handleLogin(formData.get("loginEmail"), formData.get("loginPassword"));
});

document.querySelector("#signup-form").addEventListener("submit", (e) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    handleSignup(
        formData.get("signupName"),
        formData.get("signupEmail"),
        formData.get("signupPassword"),
        formData.get("signupRole"),
        formData.get("signupLevel"),
        formData.get("signupStore")
    );
});

/* Exibir / Ocultar campo "Nível" no cadastro conforme o Perfil selecionado */
document.querySelector("#signup-role")?.addEventListener("change", (e) => {
    const levelSelect = document.querySelector("#signup-level");
    if (e.target.value === "Solicitante") {
        levelSelect.value = "";
        levelSelect.disabled = true;
    } else {
        levelSelect.disabled = false;
        if (!levelSelect.value) levelSelect.value = "N1";
    }
});

/* Submit do form de chamados */
document.querySelector("#ticket-form").addEventListener("submit", (event) => {
    event.preventDefault();
    if (dialogMode === "new") createTicketFromForm(event.currentTarget);
    if (dialogMode === "details" && canManageTickets()) updateTicketFromForm(event.currentTarget);
    if (dialogMode === "details" && !canManageTickets()) dialog.close();
});

globalSearch.addEventListener("input", () => {
    currentPage = "tickets";
    render();
});

document.querySelector("#export-data").addEventListener("click", exportData);

/* Inicialização */
data = loadData();
render();
if (data.storageWarning) showToast(data.storageWarning, "error");